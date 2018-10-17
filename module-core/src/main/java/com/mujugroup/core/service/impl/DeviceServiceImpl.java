package com.mujugroup.core.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;

import com.lveqia.cloud.common.exception.ParamException;

import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.objeck.bean.DeviceBean;
import com.mujugroup.core.objeck.bean.StatusAidBean;
import com.mujugroup.core.objeck.bean.StatusHidBean;
import com.mujugroup.core.objeck.bean.StatusOidBean;
import com.mujugroup.core.mapper.BeanMapper;
import com.mujugroup.core.mapper.DeviceMapper;
import com.mujugroup.core.model.Device;
import com.mujugroup.core.objeck.vo.DeviceVo;
import com.mujugroup.core.service.DeviceService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    private final BeanMapper beanMapper;
    private final DeviceMapper deviceMapper;
    private final HospitalMapper hospitalMapper;
    private final MapperFactory mapperFactory;


    @Autowired
    public DeviceServiceImpl(DeviceMapper deviceMapper, BeanMapper beanMapper, HospitalMapper hospitalMapper, MapperFactory mapperFactory) {
        this.deviceMapper = deviceMapper;
        this.beanMapper = beanMapper;
        this.hospitalMapper = hospitalMapper;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public boolean insert(DeviceVo deviceVo) throws ParamException {
        if (deviceMapper.isExistMac(deviceVo.getDid()) > 0) throw new ParamException("该did已存在");
        if (deviceMapper.isExistCode(deviceVo.getBid()) > 0) throw new ParamException("该bid已存在");
        mapperFactory.classMap(DeviceVo.class, Device.class)
                .field("did", "mac")
                .field("bid", "code")
                .byDefault().register();
        ;
        Device device = mapperFactory.getMapperFacade().map(deviceVo, Device.class);
        return deviceMapper.insert(device);
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
