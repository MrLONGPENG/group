package com.mujugroup.wx.service;


import com.lveqia.cloud.common.dto.AidHidOidDto;
import com.lveqia.cloud.common.dto.OrderDto;

import java.util.List;

public interface FeignService {

    List<OrderDto> getOrderList(AidHidOidDto aidHidOidDto);
}
