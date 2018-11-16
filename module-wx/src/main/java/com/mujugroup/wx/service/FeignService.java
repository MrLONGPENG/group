package com.mujugroup.wx.service;


import com.lveqia.cloud.common.objeck.to.*;

public interface FeignService {

    PageTo<OrderTo> getOrderList(RequestTo aidHidOidDto);

    int getCountByUsingDid(String did, long time);

    PayInfoTo getPayInfoByDid(String did, int orderType);

    UptimeTo getUptimeTo(int aid, int hid, int oid);
}
