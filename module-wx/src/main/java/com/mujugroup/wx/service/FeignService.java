package com.mujugroup.wx.service;


import com.lveqia.cloud.common.objeck.to.PayInfoTo;
import com.lveqia.cloud.common.objeck.to.RequestTo;
import com.lveqia.cloud.common.objeck.to.OrderTo;
import com.lveqia.cloud.common.objeck.to.PageTo;

public interface FeignService {

    PageTo<OrderTo> getOrderList(RequestTo aidHidOidDto);

    int getCountByUsingDid(String did, long time);

    PayInfoTo getPayInfoByDid(String did);
}
