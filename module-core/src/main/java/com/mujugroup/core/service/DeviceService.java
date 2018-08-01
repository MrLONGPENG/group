package com.mujugroup.core.service;
import com.mujugroup.core.bean.DeviceBean;
import com.mujugroup.core.bean.StatusAidBean;
import com.mujugroup.core.bean.StatusHidBean;
import com.mujugroup.core.bean.StatusOidBean;
import com.mujugroup.core.model.Device;

import java.util.List;


public interface DeviceService {

    DeviceBean findDeviceBeanByDid(String did);

    List<Device> findListAll();

    List<Device> findListByStatus(int status);

    List<StatusAidBean> findGroupByAid(int aid);

    List<StatusHidBean> findGroupByHid(int aid, int hid);

    List<StatusOidBean> findGroupByOid(int aid, int hid, int oid);


}
