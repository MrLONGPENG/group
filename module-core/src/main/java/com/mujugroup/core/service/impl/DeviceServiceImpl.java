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
import com.mujugroup.core.objeck.vo.device.DeviceVo;
import com.mujugroup.core.objeck.vo.device.PutVo;
import com.mujugroup.core.service.DeviceService;
import ma.glasnost.orika.MapperFactory;
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
        Device device = deviceMapper.findById(Integer.parseInt(id));
        if (device == null) {
            throw new ParamException("当前设备不存在,请重新选择");
        }
        device.setStatus(Device.TYPE_DELETE);
        return deviceMapper.update(device);
    }

    @Transactional
    @Override
    public boolean modifyDevice(int uid, PutVo devicePutVo) throws ParamException {
        if (devicePutVo.getId() == null) throw new ParamException("设备编号不能为空");
        Device device = deviceMapper.findById(devicePutVo.getId());
        if (device == null) {
            throw new ParamException("当前设备不存在,请重新选择");
        }
        if (devicePutVo.getAid() == null) throw new ParamException("请选择代理商");
        if (devicePutVo.getHid() == null) throw new ParamException("请选择医院");
        if (devicePutVo.getOid() == null) throw new ParamException("请选择科室");
        if (StringUtil.isEmpty(devicePutVo.getBed())) throw new ParamException("请输入床位信息");
        if (devicePutVo.getStatus() == null) throw new ParamException("请选择设备状态");
        if (devicePutVo.getRun() == null) throw new ParamException("请选择是否为商用");
        if (devicePutVo.getPay() == null) throw new ParamException("请选择是否为扫码支付");
        //将VO对象转为实体对象
        Device model = deviceVoToDevice(devicePutVo, PutVo.class);
        if ((!device.getAgentId().equals(devicePutVo.getAid())) || (!device.getHospitalId().equals(devicePutVo.getHid())) || (!device.getDepart().equals(devicePutVo.getOid()))) {
            //将原有数据的状态设置为删除状态
            device.setStatus(Device.TYPE_DELETE);
            //更新原有数据
            boolean result = deviceMapper.update(device);
            Device entity = bindDevice(uid, model, device);
            result &= deviceMapper.insert(entity);
            return result;
        } else {
            model.setUpdateTime(new Date());
            model.setUpdateId(uid);
            return deviceMapper.update(model);
        }
    }

    private Device bindDevice(int uid, Device model, Device device) {
        Device entity = new Device();
        entity.setMac(device.getMac());
        entity.setCode(device.getCode());
        entity.setHospitalBed(model.getHospitalBed());
        //设为启用状态
        entity.setStatus(Device.TYPE_ENABLE);
        //进行添加操作,该数据记录的创建时间
        entity.setCrtTime(new Date());
        //进行添加操作,该数据记录的更新时间
        entity.setUpdateTime(entity.getCrtTime());
        entity.setCrtId(uid);
        entity.setUpdateId(uid);
        //设置代理商
        entity.setAgentId(model.getAgentId());
        //设置医院
        entity.setHospitalId(model.getHospitalId());
        //设置科室
        entity.setDepart(model.getDepart());
        entity.setRemark(model.getRemark() == null ? device.getRemark() : model.getRemark());
        entity.setRun(model.getRun() == null ? (device.getRun() == null ? 0 : device.getRun()) : model.getRun());
        entity.setPay(model.getPay() == null ? (device.getPay() == null ? 0 : device.getPay()) : model.getPay());
        return entity;
    }

    @Transactional
    @Override
    public boolean insert(int uid, DeviceVo vo) throws ParamException {
        if (!StringUtil.isNumeric(vo.getDid()) || vo.getDid().length() != 9) throw new ParamException("无效DID编号");
        if (!StringUtil.isNumeric(vo.getBid()) || vo.getBid().length() != 19) throw new ParamException("无效BID编号");
        if (deviceMapper.isExistMac(vo.getDid()) > 0) throw new ParamException("该DID已存在,无法重复激活");
        if (deviceMapper.isExistCode(vo.getBid()) > 0) throw new ParamException("该BID已存在,无法重复激活");
        if (vo.getHid() == null) throw new ParamException("请选择医院");
        if (vo.getOid() == null) throw new ParamException("请选择科室");
        Device device = deviceVoToDevice(vo, DeviceVo.class);
        device.setAgentId(getAidOid(vo.getHid(), vo.getOid()));
        device.setCrtId(uid);
        device.setUpdateId(uid);
        device.setCrtTime(new Date());
        device.setUpdateTime(new Date());
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
                .field("aid", "agentId")
                .byDefault().register();
        return mapperFactory.getMapperFacade().map(obj, Device.class);
    }


}
