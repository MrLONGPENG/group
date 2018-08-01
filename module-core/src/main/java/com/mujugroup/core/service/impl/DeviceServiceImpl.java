package com.mujugroup.core.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.mujugroup.core.bean.DeviceBean;
import com.mujugroup.core.bean.StatusAidBean;
import com.mujugroup.core.bean.StatusHidBean;
import com.mujugroup.core.bean.StatusOidBean;
import com.mujugroup.core.mapper.BeanMapper;
import com.mujugroup.core.mapper.DeviceMapper;
import com.mujugroup.core.model.Device;
import com.mujugroup.core.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    private final BeanMapper beanMapper;
    private final DeviceMapper deviceMapper;
    @Autowired
    public DeviceServiceImpl(DeviceMapper deviceMapper, BeanMapper beanMapper) {
        this.deviceMapper = deviceMapper;
        this.beanMapper = beanMapper;
    }


    @Override
    public DeviceBean findDeviceBeanByDid(String did) {
        return beanMapper.findDeviceBeanByDid(did);
    }

    @Override
    public List<Device> findListAll() {
        return deviceMapper.findListAll();
    }

    @Override
    public List<Device> findListByStatus(int status) {
        return deviceMapper.findListByStatus(status);
    }

    @Override
    @MergeResult
    public List<StatusAidBean> findGroupByAid(int aid) {
        return deviceMapper.findGroupByAid(aid);
    }

    @Override
    @MergeResult
    public List<StatusHidBean> findGroupByHid(int aid, int hid) {
        return deviceMapper.findGroupByHid(aid, hid);
    }

    @Override
    @MergeResult
    public List<StatusOidBean> findGroupByOid(int aid, int hid, int oid) {
        return deviceMapper.findGroupByOid(aid, hid, oid);
    }
}
