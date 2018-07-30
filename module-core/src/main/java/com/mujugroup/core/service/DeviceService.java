package com.mujugroup.core.service;
import com.mujugroup.core.bean.DeviceBean;
import com.mujugroup.core.model.Device;

import java.util.List;


public interface DeviceService {

    DeviceBean findDeviceBeanByDid(String did);

    List<Device> findListAll();

    List<Device> findListByStatus(int status);
}
