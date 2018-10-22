package com.mujugroup.core.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;

import com.lveqia.cloud.common.exception.ParamException;

import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.objeck.bean.DeviceBean;
import com.mujugroup.core.objeck.bean.StatusAidBean;
import com.mujugroup.core.objeck.bean.StatusHidBean;
import com.mujugroup.core.objeck.bean.StatusOidBean;
import com.mujugroup.core.mapper.BeanMapper;
import com.mujugroup.core.mapper.DeviceMapper;
import com.mujugroup.core.model.Device;
import com.mujugroup.core.objeck.vo.Device.DeviceVo;
import com.mujugroup.core.objeck.vo.Device.PutVo;
import com.mujugroup.core.service.DeviceService;
import ma.glasnost.orika.MapperFactory;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Transactional
    @Override
    public boolean delete(String id) throws ParamException {
        if (StringUtil.isEmpty(id)) throw new ParamException("设备编号不能为空");
        if (!StringUtil.isNumeric(id)) throw new ParamException("设备编号必须为数字");
        if (deviceMapper.findById(Integer.parseInt(id)) == null) {
            throw new ParamException("当前设备不存在,请重新选择");
        }
        return deviceMapper.deleteById(Integer.parseInt(id));
    }

    @Transactional
    @Override
    public boolean modifyDevice(String id, PutVo devicePutVo) throws ParamException {
        if (StringUtil.isEmpty(id)) throw new ParamException("设备编号不能为空");
        if (deviceMapper.findById(Integer.parseInt(id)) == null) {
            throw new ParamException("当前设备不存在,请重新选择");
        }
        if (devicePutVo.getHid() == null) throw new ParamException("请选择医院");
        if (devicePutVo.getOid() == null) throw new ParamException("请选择科室");
        if (StringUtil.isEmpty(devicePutVo.getBed())) throw new ParamException("请输入床位信息");
        if (devicePutVo.getStatus() == null) throw new ParamException("请选择设备状态");
        if (devicePutVo.getRun() == null) throw new ParamException("请选择是否为商用");
        if (devicePutVo.getPay() == null) throw new ParamException("请选择是否为扫码支付");
        devicePutVo.setId(Integer.parseInt(id));
        return deviceMapper.update(deviceVoToDevice(devicePutVo, PutVo.class));

    }

    @Transactional
    @Override
    public boolean insert(int uid, DeviceVo vo) throws ParamException {
        if (!StringUtil.isNumeric(vo.getDid()) || vo.getDid().length() != 9) throw new ParamException("无效DID编号");
        if (!StringUtil.isNumeric(vo.getBid()) || vo.getBid().length() != 19) throw new ParamException("无效BID编号");
        if (deviceMapper.isExistMac(vo.getDid()) > 0) throw new ParamException("该DID已存在,无法重复激活");
        if (deviceMapper.isExistCode(vo.getBid()) > 0) throw new ParamException("该BID已存在,无法重复激活");
        Device device = deviceVoToDevice(vo, DeviceVo.class);
        device.setAgentId(getAidOid(vo.getHid(), vo.getOid()));
        device.setCrtId(uid);
        device.setUpdateId(uid);
        device.setCrtTime(new Date());
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

    private int getAidOid(int hid, int oid) throws ParamException {
        String aid = hospitalMapper.getAidByHidOid(oid, hid);
        if (StringUtil.isEmpty(aid)) throw new ParamException("医院或科室编号有误,请重新输入");
        return Integer.parseInt(aid);
    }

    /**
     * 将VO对象转为Model
     *
     * @param obj
     * @param voType
     * @return
     */
    private Device deviceVoToDevice(Object obj, Class<?> voType) {
        mapperFactory.classMap(voType, Device.class)
                .field("did", "mac")
                .field("bid", "code")
                .field("hid", "hospitalId")
                .field("oid", "depart")
                .field("bed", "hospitalBed")
                .byDefault().register();
        return mapperFactory.getMapperFacade().map(obj, Device.class);
    }


}
