package com.mujugroup.wx.service;


import com.lveqia.cloud.common.objeck.to.AidHidOidTO;
import com.lveqia.cloud.common.objeck.to.OrderTO;
import com.lveqia.cloud.common.objeck.to.PageTO;

public interface FeignService {

    PageTO<OrderTO> getOrderList(AidHidOidTO aidHidOidDto);
    int  getCountByUsingDid(String did,long time);
}
