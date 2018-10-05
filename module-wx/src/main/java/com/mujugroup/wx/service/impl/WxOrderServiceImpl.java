package com.mujugroup.wx.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.objeck.to.AidHidOidTO;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.wx.bean.OrderBean;
import com.mujugroup.wx.mapper.WxOrderMapper;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.model.WxOrder;
import com.mujugroup.wx.service.SessionService;
import com.mujugroup.wx.service.WxOrderService;
import com.mujugroup.wx.service.feign.ModuleCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;


@Service("wxOrderService")
public class WxOrderServiceImpl implements WxOrderService {

    private final WxOrderMapper wxOrderMapper;
    private final SessionService sessionService;
    private final ModuleCoreService moduleCoreService;
    private final Logger logger = LoggerFactory.getLogger(WxOrderServiceImpl.class);
    @Autowired
    public WxOrderServiceImpl(WxOrderMapper wxOrderMapper, SessionService sessionService
            , ModuleCoreService moduleCoreService) {
        this.wxOrderMapper = wxOrderMapper;
        this.sessionService = sessionService;
        this.moduleCoreService = moduleCoreService;
    }

    @Override
    public WxOrder addOrder(String did, String openId, String aid, String hid, String oid, String orderNo, WxGoods wxGoods) {
        WxOrder wxOrder = new WxOrder();
        wxOrder.setDid(Long.parseLong(did));
        wxOrder.setOpenId(openId);
        wxOrder.setTradeNo(orderNo);
        wxOrder.setAid(Integer.parseInt(aid));
        wxOrder.setHid(Integer.parseInt(hid));
        wxOrder.setOid(Integer.parseInt(oid));
        wxOrder.setGid(wxGoods.getId());
        wxOrder.setPayPrice(wxGoods.getPrice());
        wxOrder.setPayStatus(WxOrder.TYPE_PAY_WAITING);
        wxOrder.setOrderType(getOrderType(wxGoods.getType()));
        return wxOrderMapper.insert(wxOrder)? wxOrder:null;
    }

    /**
     * 根据商品类型，转换成订单类型
     */
    private Integer getOrderType(Integer type) {
        switch (type){
            case WxGoods.TYPE_MIDDAY : return WxOrder.ORDER_TYPE_MIDDAY ;
            case WxGoods.TYPE_NIGHT  : return WxOrder.ORDER_TYPE_NIGHT;
        }
        return 0;
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
    public List<WxOrder> listSelfOrder(String sessionThirdKey) {
        String openId = sessionService.getOpenId(sessionThirdKey);
        return  wxOrderMapper.findListBySelf(openId, WxOrder.TYPE_PAY_SUCCESS);
    }

    @Override
    public OrderBean details(String sessionThirdKey, String tradeNo) {
        WxOrder wxOrder = findOrderByNo(tradeNo);
        if(wxOrder!=null){
            OrderBean orderBean = new OrderBean(wxOrder);
            String did = StringUtil.autoFillDid(wxOrder.getDid());
            String result = moduleCoreService.deviceQuery(did);
            if(result!=null){
                JsonObject returnData = new JsonParser().parse(result).getAsJsonObject();
                if(returnData.get("code").getAsInt() == 200 && returnData.has("data")) {
                    JsonObject data = returnData.getAsJsonObject("data");
                    orderBean.setHospitalBed(data.get("hospitalBed").getAsString());
                    orderBean.setHospital(data.getAsJsonObject("hospital").get("name").getAsString());
                    orderBean.setAddress(data.getAsJsonObject("hospital").get("address").getAsString());
                    orderBean.setDepartment(data.getAsJsonObject("department").get("name").getAsString());
                }
            }else{
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


    @Override
    public List<DBMap> getPayCountByAid(String aid) {
        return wxOrderMapper.getPayCountByAid(aid, DateUtil.getLastDay());
    }

    @Override
    public List<DBMap> getPayCountByHid(String aid, String hid) {
        return wxOrderMapper.getPayCountByHid(aid, hid, DateUtil.getLastDay());
    }


    /**
     * 根据传入的起止时间戳，计算当期使用数
     */
    @Override
    @Cacheable(value = "wx-order-usage-count")
    public String getUsageCount(String aid, String hid, String oid, String start, String end) {
        logger.debug("getUsageCount real-time data");
        return String.valueOf(wxOrderMapper.getUsageCount(aid, hid, oid
                , Long.parseLong(start), Long.parseLong(end)).getCount1());
    }

    /**
     * 根据传入的日期格式，计算采用日累加方式
     * @param date 格式 yyyyMM yyyyMMdd yyyyMMdd-yyyyMMdd
     */
    @Override
    @Cacheable(value = "wx-order-usage-count-date")
    public String getUsageCountByDate(String aid, String hid, String oid, String date) {
        logger.debug("getUsageCountByDate real-time data");
        long timestamp;
        String avgCount;
        if(date.length() == 17) {       // 粒度--周
            timestamp = DateUtil.toTimestamp(date.substring(0,8), DateUtil.TYPE_DATE_08);
            avgCount = appendUsageCount(aid, hid, oid, timestamp, 7);
        }else if(date.length() == 6) {  // 粒度--月
            timestamp = DateUtil.toTimestamp(date, DateUtil.TYPE_MONTH);
            avgCount = appendUsageCount(aid, hid, oid, timestamp, DateUtil.getDay(date));
        }else{ // 其他默认粒度--日
            timestamp = DateUtil.toTimestamp(date, DateUtil.TYPE_DATE_08);
            avgCount = appendUsageCount(aid, hid, oid, timestamp, 1);
        }
        return avgCount;
    }

    /**
     * 根据传入的日期格式，计算采用每日均使用率
     * @param date 格式 yyyyMM yyyyMMdd yyyyMMdd-yyyyMMdd
     */
    @Override
    @Cacheable(value = "wx-order-usage-rate-date")
    public String getUsageRate(String aid, String hid, String oid, String date) {
        logger.debug("getUsageRate real-time data");
        String avgCount;
        long timestamp;
        if(date.length() == 17) {       // 粒度--周
            timestamp = DateUtil.toTimestamp(date.substring(0,8), DateUtil.TYPE_DATE_08);
            avgCount = averageUsageRate(aid, hid, oid, timestamp, 7);
        }else if(date.length() == 6) {  // 粒度--月
            timestamp = DateUtil.toTimestamp(date, DateUtil.TYPE_MONTH);
            avgCount = averageUsageRate(aid, hid, oid, timestamp, DateUtil.getDay(date));
        }else{ // 其他默认粒度--日
            timestamp = DateUtil.toTimestamp(date, DateUtil.TYPE_DATE_08);
            avgCount = averageUsageRate(aid, hid, oid, timestamp, 1);
        }
        return String.valueOf(avgCount);
    }

    /**
     * 获取指定时间内、指定条件下的利润总和
     * @param date 格式 yyyyMM yyyyMMdd yyyyMMdd-yyyyMMdd
     */
    @Override
    public String getTotalProfitByDate(String aid, String hid, String oid, String date) {
        long start, end;
        if(date.length() == 17) {       // 粒度--周
            start = DateUtil.toTimestamp(date.substring(0,8), DateUtil.TYPE_DATE_08);
            end = start + Constant.TIMESTAMP_DAYS_7;
        }else if(date.length() == 6) {  // 粒度--月
            start = DateUtil.toTimestamp(date, DateUtil.TYPE_MONTH);
            end = start + Constant.TIMESTAMP_DAYS_1 * DateUtil.getDay(date);
        }else{ // 其他默认粒度--日
            start = DateUtil.toTimestamp(date, DateUtil.TYPE_DATE_08);
            end = start + Constant.TIMESTAMP_DAYS_1;
        }
        return getTotalProfit(aid, hid, oid,null,null, start, end);
    }

    /**
     * 获取指定时间内、指定条件下的利润总和
     */
    @Override
    public String getTotalProfit(String aid, String hid, String oid, String start, String end) {
        return getTotalProfit(aid, hid, oid,null,null, Long.parseLong(start), Long.parseLong(end));
    }
    /**
     * 获取指定时间内、指定条件下的利润总和
     */
    @Override
    public String getTotalProfit(String aid, String hid, String oid, String did, String tradeNo, long start, long end) {
        return wxOrderMapper.getTotalProfit(aid, hid, oid, did, tradeNo, start, end);
    }

    @Override
    public List<WxOrder> findList(String aid, String hid, String oid, long start, long end, String tradeNo, int orderType) {
        return wxOrderMapper.findList(aid, hid, oid, start, end, tradeNo, orderType);
    }

    @Override
    public List<WxOrder> findList(AidHidOidTO dto) {
        return findList(dto.getAid(), dto.getHid(), dto.getOid(), dto.getStart(), dto.getEnd()
                , dto.getTradeNo(), dto.getOrderType());
    }

    /**
     * 周、月使用率，采用日均方法计算平均值
     */
    private String averageUsageRate(String aid, String hid, String oid, long timestamp, int days) {
        double avgCount =0, allCount;
        long start,end;
        int[] arrUsage = new int[days];
        Object[] keys = new String[days];
        for (int i = 0; i < days; i++) {
            start = timestamp + i * Constant.TIMESTAMP_DAYS_1;
            end = timestamp + (i+1) * Constant.TIMESTAMP_DAYS_1;
            keys[i] = StringUtil.toLinkByAnd(aid, hid, oid, end);
            arrUsage[i] = wxOrderMapper.getUsageCount(aid, hid, oid, start, end).getCount1();
        }
        Map<String, String> map = moduleCoreService.getTotalActiveCount(StringUtil.toLink(keys));
        for (int i = 0; i < days; i++) {
            allCount = Double.parseDouble(map.get(keys[i].toString()));
            if(allCount !=0) avgCount += arrUsage[i] / allCount;
        }
        return StringUtil.getPercent(avgCount, Double.valueOf(days));
    }

    /**
     * 周、月使用数，采用累加方式
     */
    private String appendUsageCount(String aid, String hid, String oid, long timestamp, int days) {
        int avgCount = 0;
        for (int i = 0; i < days; i++) {
            avgCount += wxOrderMapper.getUsageCount(aid, hid, oid, timestamp + i * Constant.TIMESTAMP_DAYS_1
                    ,timestamp + (i+1) * Constant.TIMESTAMP_DAYS_1).getCount1();
        }
        return String.valueOf(avgCount);
    }


}
