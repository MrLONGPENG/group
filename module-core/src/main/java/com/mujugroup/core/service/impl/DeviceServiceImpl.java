package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.AESUtil;
import com.lveqia.cloud.common.StringUtil;
import com.mujugroup.core.bean.DeviceBean;
import com.mujugroup.core.mapper.BeanMapper;
import com.mujugroup.core.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    private final BeanMapper beanMapper;

    @Autowired
    public DeviceServiceImpl(BeanMapper beanMapper) {
        this.beanMapper = beanMapper;
    }


    @Override
    public DeviceBean findDeviceBeanByDid(String did) {
        return beanMapper.findDeviceBeanByDid(did);
    }
}
