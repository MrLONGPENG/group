package com.mujugroup.core.service.impl;

import com.github.wxiaoqi.merge.annonation.MergeResult;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.mapper.BeanMapper;
import com.mujugroup.core.mapper.DeviceMapper;
import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.model.Device;
import com.mujugroup.core.objeck.bean.DeviceBean;
import com.mujugroup.core.objeck.bean.StatusAidBean;
import com.mujugroup.core.objeck.bean.StatusHidBean;
import com.mujugroup.core.objeck.bean.StatusOidBean;
import com.mujugroup.core.objeck.bo.DeviceBO;
import com.mujugroup.core.objeck.vo.device.AddVo;
import com.mujugroup.core.objeck.vo.device.PutVo;
import com.mujugroup.core.service.DeviceService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @Transactional
    public boolean delete(String id) throws ParamException {
        if (StringUtil.isEmpty(id)) throw new ParamException("设备编号不能为空");
        if (!StringUtil.isNumeric(id)) throw new ParamException("设备编号必须为数字");
        Device device = deviceMapper.findById(Integer.parseInt(id));
        if (device == null) throw new ParamException("当前设备不存在,请重新选择");
        device.setStatus(Device.TYPE_DELETE);
        return deviceMapper.update(device);
    }

    @Override
    @MergeResult
    public List<DeviceBO> findDeviceList(String did, String bid
            , String bed, String aid, String hid
            , String oid, int status) {
        List<Device> list = deviceMapper.findListAll(did, bid , bed, aid, hid , oid, status);
        mapperFactory.classMap(Device.class, DeviceBO.class)
                .field("crtId", "name")
                .field("crtId", "crtId")
                .field("agentId", "agentName")
                .field("agentId", "agentId")
                .field("hospitalId", "hospitalName")
                .field("hospitalId", "hospitalId")
                .field("depart", "departmentName")
                .field("depart", "depart")
                .byDefault().register();
        return mapperFactory.getMapperFacade().mapAsList(list, DeviceBO.class);

    }


    @Override
    @Transactional
    public boolean modifyDevice(int uid, PutVo putVo) throws ParamException {
        Device device = deviceMapper.findById(putVo.getId());
        if (device == null) throw new ParamException("当前设备不存在,请重新选择");
        if (device.getStatus() == Device.TYPE_DELETE) throw new ParamException("已经删除设备无法修改,请重新选择");

        Device model = deviceVoToDevice(putVo, PutVo.class);   //将VO对象转为实体对象
        if ((putVo.getAid() != null && !putVo.getAid().equals(device.getAgentId()))
                || (putVo.getHid() != null && !putVo.getHid().equals(device.getHospitalId()))
                || (putVo.getOid() != null && !putVo.getOid().equals(model.getDepart()))) {
            //将原有数据的状态设置为删除状态
            Date lastTime = deviceMapper.findLastDeleteTime(device.getDid(), Device.TYPE_DELETE);
            if (lastTime != null && lastTime.getTime() / 1000 > DateUtil.getTimesMorning()) {
                throw new ParamException("该设备当天已修改过代理商、医院或科室信息，无法再次修改");
            }
            device.setUpdateTime(new Date());
            device.setStatus(Device.TYPE_DELETE);
            Device entity = bindDevice(uid, model, device);
            if (getAidOid(entity.getHospitalId(), entity.getDepart()) != entity.getAgentId()) {
                throw new ParamException("代理商与医院或科室编号有误,请重新输入");
            }
            //更新原有数据
            boolean result = deviceMapper.update(device);
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
        entity.setDid(device.getDid());
        entity.setBid(device.getBid());
        entity.setCrtId(uid);
        entity.setUpdateId(uid);
        entity.setCrtTime(new Date());                //进行添加操作,该数据记录的创建时间
        entity.setUpdateTime(entity.getCrtTime());    //进行添加操作,该数据记录的更新时间
        entity.setStatus(model.getStatus() == null ? device.getStatus() : model.getStatus());
        entity.setAgentId(model.getAgentId() == null ? device.getAgentId() : model.getAgentId());
        entity.setHospitalId(model.getHospitalId() == null ? device.getHospitalId() : model.getHospitalId());
        entity.setDepart(model.getDepart() == null ? device.getDepart() : model.getDepart());
        entity.setRun(model.getRun() == null ? device.getRun() : model.getRun());
        entity.setBell(model.getBell() == null ? device.getBell() : model.getBell());
        entity.setRemark(model.getRemark() == null ? device.getRemark() : model.getRemark());
        entity.setHospitalBed(model.getHospitalBed() == null ? device.getHospitalBed() : model.getHospitalBed());
        return entity;
    }


    @Override
    @Transactional
    public boolean insert(int uid, AddVo vo) throws ParamException {
        if (deviceMapper.isExistDid(vo.getDid()) > 0) throw new ParamException("该DID已存在,无法重复激活");
        if (deviceMapper.isExistBid(vo.getBid()) > 0) throw new ParamException("该BID已存在,无法重复激活");
        Device device = deviceVoToDevice(vo, AddVo.class);
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
    public List<Device> findListAll(String did, String bid, String bed, String aid, String hid, String oid, int status) {
        return deviceMapper.findListAll(did, bid , bed, aid, hid , oid, status);
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
                .field("hid", "hospitalId")
                .field("oid", "depart")
                .field("bed", "hospitalBed")
                .field("aid", "agentId")
                .byDefault().register();
        return mapperFactory.getMapperFacade().map(obj, Device.class);
    }


}
