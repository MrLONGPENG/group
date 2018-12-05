package com.mujugroup.wx.service.impl;

import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.to.*;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.wx.bean.OrderBean;
import com.mujugroup.wx.config.MyConfig;
import com.mujugroup.wx.mapper.WxOrderMapper;
import com.mujugroup.wx.model.*;
import com.mujugroup.wx.service.*;
import com.mujugroup.wx.service.feign.ModuleCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("wxOrderService")
public class WxOrderServiceImpl implements WxOrderService {

    private final WxOrderMapper wxOrderMapper;
    private final SessionService sessionService;
    private final ModuleCoreService moduleCoreService;
    private final WxRecordMainService wxRecordMainService;
    private final PayApiService payApiService;
    private final WxRefundRecordService wxRefundRecordService;
    private final WxUsingService wxUsingService;
    private final Logger logger = LoggerFactory.getLogger(WxOrderServiceImpl.class);


    @Autowired
    public WxOrderServiceImpl(WxOrderMapper wxOrderMapper, SessionService sessionService
            , ModuleCoreService moduleCoreService, WxRecordMainService wxRecordMainService, PayApiService payApiService, WxRefundRecordService wxRefundRecordService, WxUsingService wxUsingService) {
        this.wxOrderMapper = wxOrderMapper;
        this.sessionService = sessionService;
        this.moduleCoreService = moduleCoreService;
        this.wxRecordMainService = wxRecordMainService;
        this.payApiService = payApiService;
        this.wxRefundRecordService = wxRefundRecordService;
        this.wxUsingService = wxUsingService;
    }

    @Override
    public WxOrder findLastOrderByDid(String did) {
        return wxOrderMapper.findLastOrderByDid(did);
    }

    @Override
    public WxOrder findOrderByNo(String orderNo) {
        return wxOrderMapper.findByNo(orderNo);
    }

    @Override
    public void update(WxOrder wxOrder) {
        wxOrderMapper.update(wxOrder);
    }

    @Override
    public void insert(WxOrder wxOrder) {
        wxOrderMapper.insert(wxOrder);
    }

    @Override
    public List<WxOrder> listSelfOrder(String sessionThirdKey) {
        String openId = sessionService.getOpenId(sessionThirdKey);
        return wxOrderMapper.findListBySelf(openId, WxOrder.TYPE_PAY_SUCCESS);
    }

    @Override
    public OrderBean details(String sessionThirdKey, String tradeNo) {
        WxOrder wxOrder = findOrderByNo(tradeNo);
        if (wxOrder != null) {
            OrderBean orderBean = new OrderBean(wxOrder);
            String did = StringUtil.autoFillDid(wxOrder.getDid());
            InfoTo result = moduleCoreService.getDeviceInfo(did, null);
            if (result != null && !result.isIllegal()) {
                if (!result.isIllegal()) {
                    orderBean.setAddress(result.getAddress());
                    orderBean.setHospitalBed(result.getBed());
                    orderBean.setHospital(result.getHospital());
                    orderBean.setDepartment(result.getDepartment());
                }
            } else {
                orderBean.setAddress("服务器异常，无法取到实时数据");
            }
            return orderBean;
        }
        return null;
    }

    @Override
    public List<WxOrder> findListAll() {
        return wxOrderMapper.findListAll();
    }

    @Transactional
    @Override
    public Map<String, String> orderRefund(String tradeNo, Integer price) throws BaseException {
        if (StringUtil.isEmpty(tradeNo)) throw new ParamException("订单编号不能为空!");
        WxOrder wxOrder = getFinishOrderByTradeNo(tradeNo);
        if (wxOrder == null) throw new ParamException("当前订单不存在,请重新选择");
        if (price > wxOrder.getPayPrice()) throw new ParamException("退款金额不能大于实际支付金额!");
        WxRecordMain wxRecordMain = wxRecordMainService.getFinishPayRecordByNo(wxOrder.getTradeNo(), wxOrder.getOpenId());
        if (wxRecordMain == null) throw new ParamException("统一支付主表无当前记录,请重新选择");
        if (wxRecordMain.getRefundCount() > 8) throw new ParamException("当前退款次数已超过9次,无法进行退款操作");
        wxRecordMain.setRefundPrice(wxRecordMain.getRefundPrice() + price);//设置支付记录主表的退款金额
        wxRecordMain.setRefundCount(wxRecordMain.getRefundCount() + 1);//设置当前退款次数
        //调用微信退款接口,进行退款操作
        Map<String, String> map = payApiService.refund(wxRecordMain.getRefundCount(), wxOrder.getTradeNo()
                , wxRecordMain.getTotalPrice().longValue(), price.longValue()
                , "订单退款", MyConfig.REFUND_SOURCE_UNSETTLED_FUNDS);
        String wxRefundNo = wxOrder.getTradeNo() + wxRecordMain.getRefundCount();
        if (map != null && MyConfig.SUCCESS.equals(map.get("return_code")) && MyConfig.SUCCESS.equals(map.get("result_code"))) {
            wxOrder.setPayStatus(WxOrder.REFUNDING_MONEY);//设置当前订单的状态为已退款
            wxOrder.setEndTime(System.currentTimeMillis() / 1000);
            WxUsing wxUsing = wxUsingService.getWxUsingByDidAndPayTime(wxOrder.getOpenId(), wxOrder.getDid().toString(), wxOrder.getPayTime());
            if (wxUsing != null) {
                wxUsing.setDeleted(true);//设置使用状态为删除状态
                wxUsingService.update(wxUsing);
            }
            WxRefundRecord wxRefundRecord = wxRefundRecordService.bindWxRefundRecord(wxOrder.getOpenId(), wxOrder.getTradeNo()
                    , wxRefundNo, wxRecordMain.getRefundCount(), wxRecordMain.getRefundPrice()
                    , wxRecordMain.getTotalPrice(), WxRefundRecord.PAY_SUCCESS, WxRefundRecord.TYPE_ORDER_REFUND, "订单退款");
            wxOrderMapper.update(wxOrder);
            wxRecordMainService.update(wxRecordMain);
            wxRefundRecordService.insert(wxRefundRecord);
        } else {
            if (map != null) logger.debug("退款失败:{}", map.get("err_code_des"));
            WxRefundRecord wxRefundRecord = wxRefundRecordService.bindWxRefundRecord(wxOrder.getOpenId(), wxOrder.getTradeNo()
                    , wxRefundNo, wxRecordMain.getRefundCount(), wxRecordMain.getRefundPrice()
                    , wxRecordMain.getTotalPrice(), WxRefundRecord.PAY_FAIL, WxRefundRecord.TYPE_ORDER_REFUND, "订单退款");
            wxRefundRecordService.insert(wxRefundRecord);
        }
        return map;
    }

    @Override
    public List<DBMap> getPayCountByAid(String aid) {
        return wxOrderMapper.getPayCountByAid(aid, DateUtil.getLastDay());
    }

    @Override
    public List<DBMap> getPayCountByHid(String aid, String hid) {
        return wxOrderMapper.getPayCountByHid(aid, hid, DateUtil.getLastDay());
    }


    /**
     * 根据传入的日期格式，计算采用日累加方式
     *
     * @param date 格式 yyyyMM yyyyMMdd yyyyMMdd-yyyyMMdd
     */
    @Override
    @Cacheable(value = "wx-order-usage-count-date")
    public String getUsageCount(String aid, String hid, String oid, String date) {
        logger.debug("getUsageCountByDate real-time data");
        return getUsageCountFromDb(aid, hid, oid, date);
    }

    @Override
    public String getUsageCountFromDb(String aid, String hid, String oid, String date) {
        logger.debug("getUsageCountByDate real-time data");
        long timestamp;
        String avgCount;
        if (date.length() == 17) {       // 粒度--周
            timestamp = DateUtil.getDelayTimestamp(date.substring(0, 8)) + Constant.TIMESTAMP_DAYS_1;
            avgCount = appendUsageCount(aid, hid, oid, timestamp, 7);
        } else if (date.length() == 6) {  // 粒度--月
            timestamp = DateUtil.getDelayTimestamp(date) + Constant.TIMESTAMP_DAYS_1;
            avgCount = appendUsageCount(aid, hid, oid, timestamp, DateUtil.getDay(date));
        } else { // 其他默认粒度--日
            timestamp = DateUtil.getDelayTimestamp(date) + Constant.TIMESTAMP_DAYS_1;
            avgCount = appendUsageCount(aid, hid, oid, timestamp, 1);
        }
        return avgCount;
    }

    /**
     * 根据传入的日期格式，计算采用每日均使用率
     *
     * @param date 格式 yyyyMM yyyyMMdd yyyyMMdd-yyyyMMdd
     */
    @Override
    @Cacheable(value = "wx-order-usage-rate-date")
    public String getUsageRate(String aid, String hid, String oid, String date) {
        logger.debug("getUsageRate real-time data");
        String avgCount;
        long timestamp;
        if (date.length() == 17) {       // 粒度--周
            timestamp = DateUtil.getDelayTimestamp(date.substring(0, 8)) + Constant.TIMESTAMP_DAYS_1;
            avgCount = averageUsageRate(aid, hid, oid, timestamp, 7);
        } else if (date.length() == 6) {  // 粒度--月
            timestamp = DateUtil.getDelayTimestamp(date) + Constant.TIMESTAMP_DAYS_1;
            avgCount = averageUsageRate(aid, hid, oid, timestamp, DateUtil.getDay(date));
        } else { // 其他默认粒度--日
            timestamp = DateUtil.getDelayTimestamp(date) + Constant.TIMESTAMP_DAYS_1;
            avgCount = averageUsageRate(aid, hid, oid, timestamp, 1);
        }
        return String.valueOf(avgCount);
    }

    /**
     * 获取指定时间内、指定条件下的利润总和
     *
     * @param date 格式 yyyyMM yyyyMMdd yyyyMMdd-yyyyMMdd
     */
    @Override
    public String getTotalProfitByDate(String aid, String hid, String oid, String date) {
        long start, end;
        if (date.length() == 17) {       // 粒度--周
            start = DateUtil.getDelayTimestamp(date.substring(0, 8));
            end = start + Constant.TIMESTAMP_DAYS_7;
        } else if (date.length() == 6) {  // 粒度--月
            start = DateUtil.getDelayTimestamp(date);
            end = start + Constant.TIMESTAMP_DAYS_1 * DateUtil.getDay(date);
        } else { // 其他默认粒度--日
            start = DateUtil.getDelayTimestamp(date);
            end = start + Constant.TIMESTAMP_DAYS_1;
        }
        return getTotalProfit(aid, hid, oid, null, null, start, end);
    }

    /**
     * 获取指定时间内、指定条件下的利润总和
     */
    @Override
    public String getTotalProfit(String aid, String hid, String oid, String start, String end) {
        return getTotalProfit(aid, hid, oid, null, null, Long.parseLong(start), Long.parseLong(end));
    }

    /**
     * 获取指定时间内、指定条件下的利润总和
     */
    @Override
    public String getTotalProfit(String aid, String hid, String oid, String did, String tradeNo, long start, long end) {
        return wxOrderMapper.getTotalProfit(aid, hid, oid, did, tradeNo, WxOrder.ORDER_TYPE_NIGHT, start, end);
    }

    @Override
    public List<WxOrder> findList(String aid, String hid, String oid, long start, long end, String tradeNo, int orderType, String did) {
        return wxOrderMapper.findList(aid, hid, oid, start, end, tradeNo, orderType, did);
    }

    @Override
    public WxOrder getFinishOrderByTradeNo(String tradeNo) {
        return wxOrderMapper.getFinishOrderByTradeNo(tradeNo);
    }

    @Override
    public WxOrder getFinishOrderById(long id) {
        return wxOrderMapper.getFinishOrderById(id);
    }

    @Override
    public OrderTo getOrderByCondition(long did) {
        return wxOrderMapper.getOrderByCondition(did);
    }

    @Override
    public WxOrder getOrderByOpenidAndTradeNo(String openId, Integer status, String orderNo) {
        return wxOrderMapper.getOrderByOpenidAndTradeNo(openId, status, orderNo);
    }

    @Override
    public PayInfoTo getPayInfoByDid(String did, int orderType) {
        return wxOrderMapper.getPayInfoByDid(did, orderType);
    }

    @Override
    public String getOrderEndTimeByDid(String did) {
        return wxOrderMapper.getOrderEndTimeByDid(did);
    }

    @Override
    public List<WxOrder> findList(RequestTo dto) {
        return findList(dto.getAid(), dto.getHid(), dto.getOid(), dto.getStart(), dto.getEnd()
                , dto.getTradeNo(), dto.getOrderType(), dto.getDid());
    }

    /**
     * 只查询晚休且不按用户去重
     *
     * @param usage 采用usage时间查询，根据订单开始与结束时间之内按每日查询
     */
    @Override
    public int getDailyUsage(String aid, String hid, String oid, long usage) {
        return wxOrderMapper.getUsageCount(aid, hid, oid, WxOrder.ORDER_TYPE_NIGHT, 0, 0, usage).getCount2();
    }

    /**
     * 周、月使用率，采用日均方法计算平均值
     */
    private String averageUsageRate(String aid, String hid, String oid, long timestamp, int days) {
        double avgCount = 0, allCount;
        long usage;
        int[] arrUsage = new int[days];
        Object[] keys = new String[days];
        for (int i = 0; i < days; i++) { // 延后时间加一天
            usage = timestamp + i * Constant.TIMESTAMP_DAYS_1;
            keys[i] = StringUtil.toLinkByAnd(aid, hid, oid, usage);
            // 只查询晚修类型
            arrUsage[i] = getDailyUsage(aid, hid, oid, usage);
        }
        Map<String, String> map = moduleCoreService.getTotalActiveCount(StringUtil.toLink(keys));
        for (int i = 0; i < days; i++) {
            allCount = Double.parseDouble(map.get(keys[i].toString()));
            if (allCount != 0) avgCount += arrUsage[i] / allCount;
        }
        return StringUtil.getPercent(avgCount, Double.valueOf(days));
    }

    /**
     * 周、月使用数，采用累加方式
     */
    private String appendUsageCount(String aid, String hid, String oid, long timestamp, int days) {
        int avgCount = 0;
        for (int i = 0; i < days; i++) {
            avgCount += getDailyUsage(aid, hid, oid, timestamp + i * Constant.TIMESTAMP_DAYS_1);
        }
        return String.valueOf(avgCount);
    }


}
