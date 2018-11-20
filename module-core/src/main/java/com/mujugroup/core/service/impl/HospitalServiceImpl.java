package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.mapper.DepartmentMapper;
import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.model.Agent;
import com.mujugroup.core.model.Department;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.objeck.bo.HospitalBo;
import com.mujugroup.core.objeck.vo.hospital.AddVo;
import com.mujugroup.core.objeck.vo.hospital.ListVo;
import com.mujugroup.core.objeck.vo.hospital.PutVo;
import com.mujugroup.core.objeck.vo.SelectVo;
import com.mujugroup.core.service.AgentService;
import com.mujugroup.core.service.AuthDataService;
import com.mujugroup.core.service.HospitalService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author leolaurel
 */
@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService {


    private final HospitalMapper hospitalMapper;
    private final MapperFactory mapperFactory;
    private final DepartmentMapper departmentMapper;
    private final AuthDataService authDataService;
    private final AgentService agentService;


    @Autowired
    public HospitalServiceImpl(HospitalMapper hospitalMapper, MapperFactory mapperFactory
            , DepartmentMapper departmentMapper, AuthDataService authDataService, AgentService agentService) {
        this.hospitalMapper = hospitalMapper;
        this.mapperFactory = mapperFactory;
        this.departmentMapper = departmentMapper;
        this.authDataService = authDataService;
        this.agentService = agentService;
    }


    @Override
    public List<SelectVo> getAgentHospitalListByUid(String type, long uid) {
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
    public List<HospitalBo> getHospitalBoByIds(String[] array) {
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
    public List<ListVo> findAll(Map<String,String> map,int uid, String aid, String name, int provinceId, int cityId
            , int enable) throws DataException {
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && map.containsKey(CoreConfig.AUTH_DATA_AGENT)) {
            if (StringUtil.isEmpty(aid)||Constant.DIGIT_ZERO.equals(aid)) {
                return hospitalMapper.findAll(map.get(CoreConfig.AUTH_DATA_AGENT), name, provinceId, cityId, enable);
            } else {
                String[] strArray = map.get(CoreConfig.AUTH_DATA_AGENT).split(Constant.SIGN_DOU_HAO);
                if (Arrays.stream(strArray).anyMatch(s -> s.equals(aid))) {
                    return hospitalMapper.findAll(aid, name, provinceId, cityId, enable);
                } else {
                    throw new DataException("当前医院所属代理商无权限,暂无法查看");
                }
            }
        } else if (map.containsKey(CoreConfig.AUTH_DATA_ALL)) {
            return hospitalMapper.findAll(aid, name, provinceId, cityId, enable);
        } else {
            throw new DataException("当前医院所属代理商无权限,暂无法查看");
        }

    }

    @Override
    public List<SelectVo> selectAll() {
        return hospitalMapper.selectAll();
    }

    @Override
    public String getHidByAid(String aid) {
        return hospitalMapper.getHidByAid(aid);
    }

    @Override
    public List<SelectVo> getHidByUid(Integer uid) {
        return hospitalMapper.getHidByUid(uid);
    }

    @Override
    public boolean remove(int uid, String hid) throws ParamException, DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        //判断当前用户有无数据权限
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        //如果当前用户没有最高数据权限只有代理商分权限
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && map.containsKey(CoreConfig.AUTH_DATA_AGENT)) {
            String[] strArray = map.get(CoreConfig.AUTH_DATA_AGENT).split(Constant.SIGN_DOU_HAO);
            Hospital model = hospitalMapper.findById(Integer.parseInt(hid));
            if (model == null) {
                throw new ParamException("要删除的医院不存在,请重新选择");
            } else {
                if (Arrays.stream(strArray).noneMatch(s -> s.equals(model.getAgentId()))) {
                    throw new DataException("当前医院所属代理商无权限,暂无法进行删除医院的操作");
                } else {
                    List<Department> departmentList = departmentMapper.findListByHid(hid);
                    if (departmentList != null && departmentList.size() > 0) {
                        throw new ParamException("该医院下存在科室,无法进行删除");
                    } else {
                        //变更医院的状态为删除状态
                        model.setEnable(Hospital.TYPE_DELETE);
                        return hospitalMapper.update(model);
                    }
                }
            }
        } else if (map.containsKey(CoreConfig.AUTH_DATA_ALL)) {
            return hospitalMapper.deleteById(Integer.parseInt(hid));
        } else {
            throw new DataException("当前医院所属代理商无权限,暂无法进行添加医院的操作");
        }
    }

    @Override
    public boolean modify(int uid, PutVo hospitalPutVo) throws ParamException, DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        //判断当前用户有无数据权限
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        //如果当前用户没有最高数据权限只有代理商分权限
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && map.containsKey(CoreConfig.AUTH_DATA_AGENT)) {
            String[] strArray = map.get(CoreConfig.AUTH_DATA_AGENT).split(Constant.SIGN_DOU_HAO);
            if (Arrays.stream(strArray).noneMatch(s -> s.equals(hospitalPutVo.getAid()))) {
                throw new DataException("当前医院所属代理商无权限,暂无法进行修改医院的操作");
            } else {
                return modifyFunc(hospitalPutVo);
            }
        } else if (map.containsKey(CoreConfig.AUTH_DATA_ALL)) {
            return modifyFunc(hospitalPutVo);
        } else {
            throw new DataException("当前医院所属代理商无权限,暂无法进行添加医院的操作");
        }

    }

    private boolean modifyFunc(PutVo hospitalPutVo) throws ParamException {
        Hospital model = hospitalMapper.findById(hospitalPutVo.getId());
        if (model == null) throw new ParamException("要修改的医院不存在,请重新选择");
        if (model.getName() != null && hospitalPutVo.getName() != null) {
            if (!model.getName().equals(hospitalPutVo.getName())) {
                if (isExitsName(hospitalPutVo.getName()) > 0) throw new ParamException("医院名称已存在,请重新输入");
            }
        }
        if (!StringUtil.isEmpty(hospitalPutVo.getAid())) {
            Agent agent = agentService.findById(Integer.parseInt(hospitalPutVo.getAid()));
            if (agent == null) throw new ParamException("请选择正确的代理商");
            if (!hospitalPutVo.getAid().equals(model.getAgentId())) {
                throw new ParamException("暂不支持代理商的变更");
            }
        }

        Hospital hospital = hospitalVoToHospital(hospitalPutVo, PutVo.class);
        return hospitalMapper.update(hospital);
    }

    @Override
    public boolean add(int uid, AddVo addVo) throws ParamException, DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        //判断当前用户有无数据权限
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        //如果当前用户没有最高数据权限只有代理商分权限
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && map.containsKey(CoreConfig.AUTH_DATA_AGENT)) {
            String[] strArray = map.get(CoreConfig.AUTH_DATA_AGENT).split(Constant.SIGN_DOU_HAO);
            //进行医院所属代理商的权限校验
            if (Arrays.stream(strArray).noneMatch(s -> s.equals(addVo.getAid()))) {
                throw new DataException("当前医院所属代理商无权限,暂无法进行添加医院的操作");
            } else {
                return addFunc(addVo, uid);
            }
        } else if (map.containsKey(CoreConfig.AUTH_DATA_ALL)) {
            //当前用户拥有最高数据权限,直接进行添加操作
            return addFunc(addVo, uid);
        } else {
            throw new DataException("当前医院所属代理商无权限,暂无法进行添加医院的操作");
        }
    }

    private boolean addFunc(AddVo addVo, int uid) throws ParamException {
        if (!StringUtil.isNumeric(addVo.getAid())) throw new ParamException("代理商选择有误");
        if (getProvinceCity(addVo.getCid(), addVo.getPid()) <= 0) throw new ParamException("请选择正确的省市");
        Agent agent = agentService.findById(Integer.parseInt(addVo.getAid()));
        if (agent == null) throw new ParamException("请选择正确的代理商");
        if (isExitsName(addVo.getName()) > 0) throw new ParamException("医院名称已存在,请重新输入");
        Hospital hospital = hospitalVoToHospital(addVo, AddVo.class);
        //设置所在国家为中国
        hospital.setCountry(Hospital.COUNTRY_CHINA);
        hospital.setProvince(addVo.getPid());
        hospital.setCity(addVo.getCid());
        hospital.setCrtId(uid);
        hospital.setEnable(Hospital.TYPE_ENABLE);
        hospital.setCrtTime(new Date());
        return hospitalMapper.insert(hospital);
    }

    @Override
    public List<SelectVo> getHospitalListByUid(String type, long uid) {
        return hospitalMapper.getHospitalListByUid(type, uid);
    }

    @Override
    public List<SelectVo> getHospitalList(int aid, String name) {
        return hospitalMapper.getHospitalList(aid, name);
    }

    //将VO对象转为model
    private Hospital hospitalVoToHospital(Object obj, Class<?> voType) {
        mapperFactory.classMap(voType, Hospital.class)
                .field("aid", "agentId")
                .field("cid", "city")
                .field("pid", "province")
                .field("uid", "crtId")
                .byDefault().register();
        return mapperFactory.getMapperFacade().map(obj, Hospital.class);
    }
}
