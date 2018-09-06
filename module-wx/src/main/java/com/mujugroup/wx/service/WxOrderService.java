package com.mujugroup.wx.service;

import com.lveqia.cloud.common.to.AidHidOidTO;
import com.lveqia.cloud.common.util.DBMap;
import com.mujugroup.wx.bean.OrderBean;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.model.WxOrder;

import java.util.List;

public interface WxOrderService {

    WxOrder addOrder(String did, String openId, String aid, String hid, String oid, String orderNo, WxGoods wxGoods);

    WxOrder findLastOrderByDid(String did);

    WxOrder findOrderByNo(String orderNo);

    void update(WxOrder wxOrder);

    List<WxOrder> listSelfOrder(String sessionThirdKey);

    OrderBean details(String sessionThirdKey, String tradeNo);

    List<WxOrder> findListAll();

    List<DBMap> getPayCountByAid(String aid);

    List<DBMap> getPayCountByHid(String aid, String hid);

    String getUsageCount(String aid, String hid, String oid, String start, String end);

    String getUsageCountByDate(String aid, String hid, String oid, String date);

    String getUsageRate(String aid, String hid, String oid, String date);

    String getTotalProfitByDate(String aid, String hid, String oid, String date);

    String getTotalProfit(String aid, String hid, String oid, String start, String end);

    String getTotalProfit(String aid, String hid, String oid, String did, String tradeNo, long start, long end);

    List<WxOrder> findList(int aid, int hid, int oid, long start, long end, String tradeNo);

    List<WxOrder> findList(AidHidOidTO dto);



}
