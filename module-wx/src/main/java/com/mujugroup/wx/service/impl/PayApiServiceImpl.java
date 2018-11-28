package com.mujugroup.wx.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.cache.ILocalCache;
import com.mujugroup.wx.config.MyConfig;
import com.mujugroup.wx.model.*;
import com.mujugroup.wx.service.*;
import com.mujugroup.wx.service.feign.ModuleLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
    private final WxRecordMainService wxRecordMainService;
    private final WxRecordAssistService wxRecordAssistService;
    private final  WxDepositService wxDepositService;
    private final static int UNIFIED_ORDER = 1;//统一下单
    private final static int FINISH_PAY = 2;//支付完成

    @Value("${wx_url_notify}")
    String notifyUrl;


    @Value("${spring.profiles.active}")
    private String model;

    @Resource(name = "endTimeCache")
    private ILocalCache<String, Long> endTimeCache;

    @Autowired
    public PayApiServiceImpl(UsingApiService usingApiService, WxUsingService wxUsingService
            , WxGoodsService wxGoodsService, WxOrderService wxOrderService
            , ModuleLockService moduleLockService, WxRecordMainService wxRecordMainService, WxRecordAssistService wxRecordAssistService, WxDepositService wxDepositService) {
        this.usingApiService = usingApiService;
        this.wxUsingService = wxUsingService;
        this.wxGoodsService = wxGoodsService;
        this.wxOrderService = wxOrderService;
        this.moduleLockService = moduleLockService;
        this.wxRecordMainService = wxRecordMainService;
        this.wxRecordAssistService = wxRecordAssistService;
        this.wxDepositService = wxDepositService;
    }


    @Override
    public Map<String, String> requestPay(String sessionThirdKey, String did, String code, String strInfo, String ip) {
        logger.info("wx-requestPay-ip:" + ip);
        if (ip == null || ip.startsWith("0:")) ip = "116.62.228.47";
        Map<String, String> result = new HashMap<>();
        String[] arr = usingApiService.parseCode(sessionThirdKey, code);
        if (arr == null || arr.length < 5) {
            result.put("code", "203");
            result.put("info", "Code效验失败");
            return result;
        }
        String orderNo = DateUtil.dateToString(DateUtil.TYPE_DATETIME_14)
                + StringUtil.getRandomString(6, true);
       List<WxRecordAssist> wxRecordAssists = parseAssistInfo(strInfo);
        //如果当前有任意一个商品无法查出,即说明商品信息有误
        boolean hasError = wxRecordAssists.stream().anyMatch(s->{
            WxGoods wxGoods = wxGoodsService.findById(s.getGid());
            if(wxGoods != null) s.setName(wxGoods.getName());
            return  wxGoods == null || !wxGoods.getPrice().equals(s.getPrice());
        });
        if (!hasError) {
            result.put("code", "203");
            result.put("info", "商品信息有误");
            return result;
        }
        try {
            String[] mainInfo = getMainInfo(wxRecordAssists);
            Map<String, String> map = wxPay.unifiedOrder(getParamsMap(ip, arr[0], orderNo, mainInfo));
            map.put("out_trade_no", orderNo);
            WxRecordMain wxRecordMain = bindRecordMain(did, arr[0], arr[1], arr[2], arr[3], orderNo, mainInfo[1]);
            //将数据记录到支付主表
            boolean isInsert = wxRecordMainService.insert(wxRecordMain);
            if (isInsert) {
                logger.info("统一下单成功,NO:{}", orderNo);
                WxRecordAssist wxRecordAssist = new WxRecordAssist();
                for (int i = 0; i < wxRecordAssists.size(); i++) {
                    wxRecordAssist = wxRecordAssists.get(i);
                    wxRecordAssist.setMid(wxRecordMain.getId());
                    wxRecordAssist.setCrtTime(wxRecordMain.getCrtTime());
                    wxRecordAssistService.insert(wxRecordAssists.get(i));
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, String> getParamsMap(String ip, String value, String orderNo, String[] mainInfo) {
        Map<String, String> params = new HashMap<>();
        params.put("body", "木巨支付-单天计费");//商品描述
        params.put("attach", "上海木巨健康科技");
        params.put("detail", mainInfo[0]);//所选商品类型
        params.put("openid", value);//openid
        params.put("total_fee", mainInfo[1]);//总金额
        params.put("out_trade_no", orderNo);//商户订单号
        params.put("notify_url", notifyUrl);
        params.put("spbill_create_ip", ip);//订单生成的机器 IP
        params.put("trade_type", "JSAPI");
        return params;
    }

    private String[] getMainInfo(List<WxRecordAssist> wxRecordAssists) {
        int totalPrice = 0;
        StringBuffer sb = new StringBuffer();
        for (WxRecordAssist assist :wxRecordAssists){
            if(sb.length()!=0) sb.append(Constant.SIGN_PLUS);
            if(assist.getType() == 1) sb.append("押金");
            else sb.append(assist.getName());
            totalPrice += assist.getPrice();
        }
        return new String[]{ new String(sb), String.valueOf(totalPrice)};
    }

    private List<WxRecordAssist> parseAssistInfo(String strInfo) {
        List<WxRecordAssist> list = new ArrayList<>();
        String[] strArr = strInfo.split(Constant.SIGN_AND);
        WxRecordAssist wxRecordAssist;
        for (int i = 0; i < strArr.length; i++) {
            String[] arrItem = strArr[i].split(Constant.SIGN_LINE);
            wxRecordAssist = new WxRecordAssist();
            wxRecordAssist.setType(Integer.parseInt(arrItem[0]));
            wxRecordAssist.setGid(Integer.parseInt(arrItem[1]));
            wxRecordAssist.setPrice(Integer.parseInt(arrItem[2]));
            list.add(wxRecordAssist);
        }
        return list;
    }

    @Override
    public String completePay(String notifyXml) throws Exception {
        logger.debug("微信支付回调接收数据:{}", notifyXml);
        Map<String, String> map = WXPayUtil.xmlToMap(notifyXml);
        if (Constant.MODEL_DEV.equals(model) || WXPayUtil.isSignatureValid(map, wxPayConfig.getKey())
                && MyConfig.SUCCESS.equals(map.get("result_code"))
                && MyConfig.SUCCESS.equals(map.get("return_code"))) {
            String orderNo = map.get("out_trade_no");
            //更新统一支付状态
            WxRecordMain wxRecordMain = wxRecordMainService.findMainRecordByNo(orderNo);
            if (wxRecordMain != null) {
                wxRecordMain.setTransactionId(map.get("transaction_id"));
                wxRecordMain.setPayStatus(FINISH_PAY);
                wxRecordMainService.update(wxRecordMain);
            }
            List<WxRecordAssist> list= wxRecordMain.getAssistList();
            for (WxRecordAssist assist :list){
                if(assist.getType() == 1){ // 押金

                }else if(assist.getType() ==2 || assist.getType() ==3){ //兼容之前的
                    WxOrder wxOrder = new WxOrder();
                    long payTime = System.currentTimeMillis() / 1000;
                    long endTime = endTimeCache.get(wxRecordMain.getKey(assist.getGid()));
                    //wxOrderService
                    WxUsing wxUsing = new WxUsing();
                    wxUsing.setOpenId(map.get("openid"));
                    wxUsing.setDid(wxOrder.getDid());
                    wxUsing.setPayCost(wxOrder.getPayPrice());
                    wxUsing.setPayTime(payTime);
                    wxUsing.setEndTime(endTime);
                    wxUsing.setUsing("2".equals(moduleLockService.getStatus(String.valueOf(wxOrder.getDid()))));
                    wxUsingService.insert(wxUsing);
                    usingApiService.thirdUnlock(String.valueOf(wxOrder.getDid()));
                }else{
                    logger.warn("不支持类型:{}, 待处理", assist.getType());
                }

            }

        } else {
            logger.warn("验证微信数据错误");
            logger.warn("result_code:" + map.get("result_code"));
            logger.warn("return_code:" + map.get("return_code"));
            logger.warn("out_trade_no:" + map.get("out_trade_no"));
        }
        Map<String, String> data = new HashMap<>();
        data.put("return_code", MyConfig.SUCCESS);
        data.put("return_msg", "OK");
        return WXPayUtil.mapToXml(data);
    }

    @Override
    public Map<String, String> refund(int index, String orderNo, Long totalFee, Long refundFee
            , String refundDesc, String refundAccount) {
        SortedMap<String, String> params = new TreeMap<>();
        //商户订单号和微信订单号二选一
        params.put("out_trade_no", orderNo);
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

    private WxRecordMain bindRecordMain(String did, String openId, String aid , String hid, String oid
            , String orderNo, String totalPrice) {
        WxRecordMain wxRecordMain = new WxRecordMain();
        wxRecordMain.setOpenId(openId);
        wxRecordMain.setAid(Integer.parseInt(aid));
        wxRecordMain.setDid(Long.parseLong(did));
        wxRecordMain.setHid(Integer.parseInt(hid));
        wxRecordMain.setOid(Integer.parseInt(oid));
        wxRecordMain.setTradeNo(orderNo);
        wxRecordMain.setTotalPrice(Integer.valueOf(totalPrice));
        wxRecordMain.setCrtTime(new Date());
        //设置当前支付状态为统一下单
        wxRecordMain.setPayStatus(UNIFIED_ORDER);
        wxRecordMain.setRefundCount(0);
        wxRecordMain.setRefundPrice(0);
        return wxRecordMain;
    }

    private WxRecordAssist bindRecordAssist(WxRecordAssist wxRecordAssist, Long mid, Integer gid, Integer price, Integer type) {
        wxRecordAssist.setMid(mid);
        wxRecordAssist.setGid(gid);
        wxRecordAssist.setPrice(price);
        wxRecordAssist.setType(type);
        wxRecordAssist.setCrtTime(new Date());
        return wxRecordAssist;
    }
}
