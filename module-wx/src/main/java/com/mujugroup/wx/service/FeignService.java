package com.mujugroup.wx.service;


import com.lveqia.cloud.common.to.AidHidOidTO;
import com.lveqia.cloud.common.to.OrderTO;
import com.lveqia.cloud.common.to.PageTO;

public interface FeignService {

    PageTO<OrderTO> getOrderList(AidHidOidTO aidHidOidDto);
}
