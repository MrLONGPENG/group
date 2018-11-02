package com.mujugroup.wx.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.cache.ILocalCache;
import com.mujugroup.wx.config.MyConfig;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.model.WxOrder;
import com.mujugroup.wx.model.WxUsing;
import com.mujugroup.wx.service.*;
import com.mujugroup.wx.service.feign.ModuleLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@RefreshScope
@Service("payApiService")
public class PayApiServiceImpl implements PayApiService {
    private final Logger logger = LoggerFactory.getLogger(PayApiServiceImpl.class);
    private final WXPayConfig wxPayConfig = new MyConfig();
    private final WXPay wxPay = new WXPay(wxPayConfig);
    private final UsingApiService usingApiService;
    private final WxUsingService wxUsingService;
    private final WxOrderService wxOrderService;
    private final WxGoodsService wxGoodsService;
    private final ModuleLockService moduleLockService;

    @Value("${wx_url_notify}")
    String notifyUrl;


    @Value("${spring.profiles.active}")
    private String model;

    @Resource(name = "endTimeCache")
    private ILocalCache<String, Long> endTimeCache;

    @Autowired
    public PayApiServiceImpl(UsingApiService usingApiService, WxUsingService wxUsingService
            , WxGoodsService wxGoodsService , WxOrderService wxOrderService
            , ModuleLockService moduleLockService) {
        this.usingApiService = usingApiService;
        this.wxUsingService = wxUsingService;
        this.wxGoodsService = wxGoodsService;
        this.wxOrderService = wxOrderService;
        this.moduleLockService = moduleLockService;

    }


    @Override
    public Map<String, String> requestPay(String sessionThirdKey, String did, String code, String goods, String ip) {
        logger.info("wx-requestPay-ip:"+ ip);
        if(ip==null || ip.startsWith("0:")) ip= "116.62.228.47";
        Map<String, String> result = new HashMap<>();
        String[] arr = usingApiService.parseCode(sessionThirdKey, code);
        if(arr == null || arr.length< 5) {
            result.put("code", "203");
            result.put("info", "Code效验失败");
            return result;
        }
        WxGoods wxGoods = wxGoodsService.findById(Integer.parseInt(goods));
        if(wxGoods == null){
            result.put("code", "203");
            result.put("info", "商品信息有误");
            return result;
        }
        String orderNo = DateUtil.dateToString(DateUtil.TYPE_DATETIME_14)
                + StringUtil.getRandomString(6, true);
        Map<String, String> params = new HashMap<>();
        params.put("body",  "木巨支付-单天计费" );//商品描述
        params.put("attach", "上海木巨健康科技");
        params.put("detail", wxGoods.getName());
        params.put("openid", arr[0]);//
        params.put("total_fee", String.valueOf(wxGoods.getPrice()));//总金额
        params.put("out_trade_no", orderNo);//商户订单号
        params.put("notify_url", notifyUrl);
        params.put("spbill_create_ip", ip);//订单生成的机器 IP
        params.put("trade_type", "JSAPI");
        try {
            Map<String, String> map = wxPay.unifiedOrder(params);
            map.put("out_trade_no",orderNo);
            WxOrder wxOrder =  wxOrderService.addOrder(did, arr[0], arr[2], arr[3], arr[4], orderNo, wxGoods);
            if(wxOrder!=null) logger.info("统一下单成功,NO:{}",orderNo);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String completePay(String notifyXml) throws Exception {
        logger.debug("微信支付回调接收数据:{}", notifyXml);
        Map<String, String> map = WXPayUtil.xmlToMap(notifyXml);
        if(Constant.MODEL_DEV.equals(model) ||WXPayUtil.isSignatureValid(map, wxPayConfig.getKey())
                && MyConfig.SUCCESS.equals(map.get("result_code"))
                && MyConfig.SUCCESS.equals(map.get("return_code"))){
            String orderNo = map.get("out_trade_no");
            WxOrder wxOrder = wxOrderService.findOrderByNo(orderNo);
            long payTime = System.currentTimeMillis()/1000;
            long endTime = endTimeCache.get(wxOrder.getKey());
            //logger.debug("支付时间:{} 到期时间:{}", new Date(payTime *1000), new Date(endTime *1000));
            wxOrder.setPayTime(payTime);
            wxOrder.setEndTime(endTime);
            wxOrder.setTransactionId(map.get("transaction_id"));
            wxOrder.setPayStatus(2);

            WxUsing wxUsing = new WxUsing();
            wxUsing.setOpenId(map.get("openid"));
            wxUsing.setDid(wxOrder.getDid());
            wxUsing.setPayCost(wxOrder.getPayPrice());
            wxUsing.setPayTime(payTime);
            wxUsing.setEndTime(endTime);
            wxUsing.setUsing("2".equals(moduleLockService.getStatus(String.valueOf(wxOrder.getDid()))));
            wxOrderService.update(wxOrder);
            wxUsingService.insert(wxUsing);
            usingApiService.thirdUnlock(String.valueOf(wxOrder.getDid()));
        }else{
            logger.warn("验证微信数据错误");
            logger.warn("result_code:"+map.get("result_code"));
            logger.warn("return_code:"+map.get("return_code"));
            logger.warn("out_trade_no:"+ map.get("out_trade_no"));
        }

        Map<String, String> data = new HashMap<>();
        data.put("return_code", MyConfig.SUCCESS);
        data.put("return_msg","OK");
        return WXPayUtil.mapToXml(data);
    }

    @Override
    public Map<String, String> refund(int index, String orderNo, Long totalFee, Long refundFee
            , String refundDesc, String refundAccount) {
        SortedMap<String, String> params = new TreeMap<>();
        //商户订单号和微信订单号二选一
        params.put("out_trade_no",orderNo);
        params.put("out_refund_no", orderNo + index);
        params.put("total_fee", String.valueOf(totalFee));
        params.put("refund_fee", String.valueOf(refundFee));
        params.put("refund_desc", refundDesc);
        params.put("refund_account", refundAccount);
        try {
            Map<String, String> map = wxPay.refund(params); //加入微信支付日志
            logger.debug("微信消息-传入参数{};微信输出{}", params, map);
            if (map != null && MyConfig.SUCCESS.equals(map.get("result_code"))
                    && MyConfig.SUCCESS.equals(map.get("return_code"))) {//插入数据库
                logger.debug("out_trade_no {} out_refund_no{}", orderNo, orderNo + index);
                logger.debug("refund_id {} {}", map.get("refund_id"), map.get("err_code"));
            }
            if (map != null && MyConfig.FAIL.equals(map.get("result_code"))
                    && MyConfig.SUCCESS.equals(map.get("return_code"))
                    && MyConfig.REFUND_NOT_ENOUGH_MONEY.equals(map.get("err_code"))
                    && MyConfig.REFUND_SOURCE_UNSETTLED_FUNDS.equals(refundAccount)) {
                logger.debug("未结算金额不足  使用余额退款");
                map = refund(index, orderNo, totalFee, refundFee, refundDesc, MyConfig.REFUND_SOURCE_RECHARGE_FUNDS);
            }
            return map;
        } catch (Exception e) {//微信退款接口异常
            logger.debug("微信消息-传入参数{};异常信息-{}", params, e.getMessage());
        }
        return null;
    }
}
