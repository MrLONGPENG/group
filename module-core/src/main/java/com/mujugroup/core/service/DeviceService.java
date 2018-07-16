package com.mujugroup.core.service;
import com.mujugroup.core.bean.DeviceBean;


public interface DeviceService {

    DeviceBean findDeviceBeanByDid(String did);
}
