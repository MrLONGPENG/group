package com.mujugroup.data.service;

import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.data.objeck.vo.DeviceVo;

public interface DeviceServcie {
    DeviceVo getDeviceDetailByDid(String did) throws ParamException;
}
