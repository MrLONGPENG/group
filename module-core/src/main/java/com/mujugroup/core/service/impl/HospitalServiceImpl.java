package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.mapper.DepartmentMapper;
import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.model.Department;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.objeck.bo.HospitalBO;
import com.mujugroup.core.objeck.vo.Hospital.HospitalVo;
import com.mujugroup.core.objeck.vo.Hospital.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.HospitalService;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author leolaurel
 */
@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService {


    private final HospitalMapper hospitalMapper;
    private final MapperFactory mapperFactory;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public HospitalServiceImpl(HospitalMapper hospitalMapper, MapperFactory mapperFactory, DepartmentMapper departmentMapper) {
        this.hospitalMapper = hospitalMapper;
        this.mapperFactory = mapperFactory;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public List<SelectVO> getAgentHospitalListByUid(String type, long uid) {
        return hospitalMapper.getAgentHospitalListByUid(type, uid);
    }

    @Override
    public List<Hospital> findListByAid(String aid) {
        return hospitalMapper.findListByAid(aid);
    }

    @Override
    public List<Hospital> getHospitalByRegion(String pid, String cid) {
        return hospitalMapper.getHospitalByRegion(pid, cid);
    }

    @Override
    public List<HospitalBO> getHospitalBoByIds(String[] array) {
        return hospitalMapper.getHospitalBoByIds(StringUtil.toLinkByDouHao((Object[]) array));
    }

    @Override
    public String getHospitalName(int id) {
        return hospitalMapper.getHospitalName(id);
    }


    @Override
    public Integer getProvinceCity(int cid, int pid) {
        return hospitalMapper.getProvinceCity(cid, pid);
    }

    @Override
    public Integer isExitsName(String name) {
        return hospitalMapper.isExitsName(name);
    }

    @Override
    public boolean remove(String hid) throws ParamException {
        if (StringUtil.isEmpty(hid)) throw new ParamException("请选择要删除的医院");
        if (!StringUtil.isNumeric(hid)) throw new ParamException("医院选择有误,请重新选择");
        List<Department> departmentList = departmentMapper.findListByHid(hid);
        if (departmentList != null && departmentList.size() > 0) throw new ParamException("该医院下存在科室,无法进行删除");
        return hospitalMapper.deleteById(Integer.parseInt(hid));
    }

    @Override
    public boolean modify(String id, PutVo hospitalPutVo) throws ParamException {
        if (StringUtil.isEmpty(id))throw  new ParamException("医院编号不能为空");
        if (!StringUtil.isNumeric(id))throw  new ParamException("医院编号必须为数字");
        if (isExitsName(hospitalPutVo.getName())>0) throw new ParamException("医院名称已存在,请重新输入");
        hospitalPutVo.setId(Integer.parseInt(id));
        Hospital hospital = hospitalVoToHospital(hospitalPutVo,PutVo.class);
        return hospitalMapper.update(hospital);
    }

    @Override
    public boolean add(int uid, HospitalVo hospitalVo) throws ParamException {
        if (!StringUtil.isNumeric(hospitalVo.getAid())) throw new ParamException("代理商选择有误");
        if (getProvinceCity(hospitalVo.getCid(), hospitalVo.getPid()) <= 0) throw new ParamException("请选择正确的省市");
        List<Hospital> hospitalList = findListByAid(String.valueOf(hospitalVo.getAid()));
        if (hospitalList == null || hospitalList.size() <= 0) throw new ParamException("请选择正确的代理商");
        if (StringUtil.isEmpty(hospitalVo.getName())) throw new ParamException("医院名称不能为空");
        if (isExitsName(hospitalVo.getName())>0) throw new ParamException("医院名称已存在,请重新输入");
            Hospital hospital = hospitalVoToHospital(hospitalVo, HospitalVo.class);
        hospital.setCountry(0);
        hospital.setProvince(hospitalVo.getPid());
        hospital.setCity(hospitalVo.getCid());
        hospital.setCrtId(uid);
        hospital.setEnable(22);
        hospital.setCrtTime(new Date());
        return hospitalMapper.insert(hospital);
    }

    @Override
    public List<SelectVO> getHospitalListByUid(String type, long uid) {
        return hospitalMapper.getHospitalListByUid(type, uid);
    }


    @Override
    public List<SelectVO> getHospitalList(int aid, String name) {
        return hospitalMapper.getHospitalList(aid, name);
    }

    //将VO对象转为model

    private Hospital hospitalVoToHospital(Object obj,Class<?> voType ) {
        mapperFactory.classMap(voType, Hospital.class)
                .field("aid", "agentId")
                .field("cid", "city")
                .field("pid", "province")
                .field("uid", "crtId")
                .byDefault().register();
        return mapperFactory.getMapperFacade().map(obj, Hospital.class);
    }
}
