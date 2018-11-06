package com.mujugroup.core.service;

import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.lveqia.cloud.common.objeck.to.PageTo;
import com.mujugroup.core.model.Device;
import com.mujugroup.core.objeck.bean.StatusAidBean;
import com.mujugroup.core.objeck.bean.StatusHidBean;
import com.mujugroup.core.objeck.bean.StatusOidBean;
import com.mujugroup.core.objeck.bo.DeviceBo;
import com.mujugroup.core.objeck.vo.device.AddVo;
import com.mujugroup.core.objeck.vo.device.PutVo;

import java.util.List;


public interface DeviceService {
    boolean insert(int uid, AddVo deviceVo) throws ParamException;

    List<Device> findListByStatus(int status);

    List<StatusAidBean> findGroupByAid(int aid);

    List<StatusHidBean> findGroupByHid(int aid, int hid);

    List<StatusOidBean> findGroupByOid(int aid, int hid, int oid);

    boolean modifyDevice(int uid, PutVo devicePutVo) throws ParamException;

    boolean delete(String id) throws ParamException;

    PageTo<Device> findDeviceList(String did, String bid
            , String bed, String aid, String hid
            , String oid, int status, int pageNum, int pageSize);

    List<DeviceBo> toDeviceBO(List<Device> pageList);


    InfoTo getDeviceInfo(String did, String bid);
}
