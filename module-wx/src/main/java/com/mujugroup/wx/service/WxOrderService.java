package com.mujugroup.wx.service;

import com.lveqia.cloud.common.objeck.to.PayInfoTo;
import com.lveqia.cloud.common.objeck.to.RequestTo;
import com.lveqia.cloud.common.objeck.DBMap;
import com.mujugroup.wx.bean.OrderBean;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.model.WxOrder;

import java.util.List;

public interface WxOrderService {

    WxOrder addOrder(String did, String openId, String aid, String hid, String oid, String orderNo, WxGoods wxGoods);

    WxOrder findLastOrderByDid(String did);

    WxOrder findOrderByNo(String orderNo);

    void update(WxOrder wxOrder);

    void insert(WxOrder wxOrder);

    List<WxOrder> listSelfOrder(String sessionThirdKey);

    OrderBean details(String sessionThirdKey, String tradeNo);

    List<WxOrder> findListAll();

    List<DBMap> getPayCountByAid(String aid);

    List<DBMap> getPayCountByHid(String aid, String hid);

    String getUsageCount(String aid, String hid, String oid, String date);

    String getUsageCountFromDb(String aid, String hid, String oid, String date);

    String getUsageRate(String aid, String hid, String oid, String date);

    int getDailyUsage(String aid, String hid, String oid, long usage);

    String getTotalProfitByDate(String aid, String hid, String oid, String date);

    String getTotalProfit(String aid, String hid, String oid, String start, String end);

    String getTotalProfit(String aid, String hid, String oid, String did, String tradeNo, long start, long end);

    List<WxOrder> findList(String aid, String hid, String oid, long start, long end, String tradeNo, int orderType, String did);

    List<WxOrder> findList(RequestTo dto);

    String getOrderEndTimeByDid(String did);

    PayInfoTo getPayInfoByDid(String did, int orderType);

}
