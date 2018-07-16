package com.mujugroup.wx.service;

import com.mujugroup.wx.bean.OrderBean;
import com.mujugroup.wx.model.WxGoods;
import com.mujugroup.wx.model.WxOrder;

import java.util.List;

public interface WxOrderService {

    WxOrder addOrder(String did, String openId, String aid, String hid, String oid, String orderNo, WxGoods wxGoods);

    WxOrder findOrderByNo(String orderNo);

    void update(WxOrder wxOrder);

    List<WxOrder> listSelfOrder(String sessionThirdKey);

    OrderBean details(String sessionThirdKey, String tradeNo);

    List<WxOrder> findListAll();
}
