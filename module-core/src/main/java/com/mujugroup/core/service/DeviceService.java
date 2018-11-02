package com.mujugroup.core.service;

import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.core.model.Device;
import com.mujugroup.core.objeck.bean.DeviceBean;
import com.mujugroup.core.objeck.bean.StatusAidBean;
import com.mujugroup.core.objeck.bean.StatusHidBean;
import com.mujugroup.core.objeck.bean.StatusOidBean;
import com.mujugroup.core.objeck.bo.DeviceBO;
import com.mujugroup.core.objeck.vo.device.AddVo;
import com.mujugroup.core.objeck.vo.device.PutVo;

import java.util.List;


public interface DeviceService {
    boolean insert(int uid, AddVo deviceVo) throws ParamException;

    DeviceBean findDeviceBeanByDid(String did);

    List<Device> findListAll();

    List<Device> findListByStatus(int status);

    List<StatusAidBean> findGroupByAid(int aid);

    List<StatusHidBean> findGroupByHid(int aid, int hid);

    List<StatusOidBean> findGroupByOid(int aid, int hid, int oid);

    boolean modifyDevice(int uid,PutVo devicePutVo) throws ParamException;

    boolean delete(String id) throws ParamException;

    List<DeviceBO> findDeviceList();



}
