package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.mapper.DepartmentMapper;
import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.model.Department;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.objeck.bo.HospitalBO;
import com.mujugroup.core.objeck.vo.hospital.AddVo;
import com.mujugroup.core.objeck.vo.hospital.ListVo;
import com.mujugroup.core.objeck.vo.hospital.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;
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


    @Autowired
    public HospitalServiceImpl(HospitalMapper hospitalMapper, MapperFactory mapperFactory
            , DepartmentMapper departmentMapper, AuthDataService authDataService) {
        this.hospitalMapper = hospitalMapper;
        this.mapperFactory = mapperFactory;
        this.departmentMapper = departmentMapper;
        this.authDataService = authDataService;
    }


    @Override
    public List<SelectVO> getAgentHospitalListByUid(String type, long uid)  {
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
    public Integer isExitsName(String aid, String name) {
        return hospitalMapper.isExitsName(aid, name);
    }

    @Override
    public List<ListVo> findAll(int uid, int aid, String name, int provinceId, int cityId) throws DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL)) throw new DataException("当前用户无最高数据权限，暂无法查看!");
        return hospitalMapper.findAll(aid, name, provinceId, cityId);
    }

    @Override
    public List<SelectVO> selectAll() {
        return hospitalMapper.selectAll();
    }

    @Override
    public boolean remove(int uid, String hid) throws ParamException, DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        String[] strArray = map.get(CoreConfig.AUTH_DATA_AGENT).split(",");
        //判断当前用户有无数据权限
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        //如果当前用户没有最高数据权限只有代理商分权限
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && map.containsKey(CoreConfig.AUTH_DATA_AGENT)) {
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
                        return hospitalMapper.deleteById(Integer.parseInt(hid));
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
        String[] strArray = map.get(CoreConfig.AUTH_DATA_AGENT).split(",");
        //判断当前用户有无数据权限
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        //如果当前用户没有最高数据权限只有代理商分权限
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && map.containsKey(CoreConfig.AUTH_DATA_AGENT)) {
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
        if (isExitsName(hospitalPutVo.getAid(), hospitalPutVo.getName()) > 0) throw new ParamException("医院名称已存在,请重新输入");
        List<Hospital> hospitalList = findListByAid(String.valueOf(hospitalPutVo.getAid()));
        if (hospitalList == null || hospitalList.size() <= 0) throw new ParamException("该代理商不存在");
        if (!hospitalPutVo.getAid().equals(model.getAgentId())) {
            throw new ParamException("暂不支持代理商的变更");
        }
        Hospital hospital = hospitalVoToHospital(hospitalPutVo, PutVo.class);
        return hospitalMapper.update(hospital);
    }

    @Override
    public boolean add(int uid, AddVo addVo) throws ParamException, DataException {
        Map<String, String> map = authDataService.getAuthDataByUid(uid);
        String[] strArray = map.get(CoreConfig.AUTH_DATA_AGENT).split(",");
        //判断当前用户有无数据权限
        if (map.size() == 0) throw new DataException("当前用户没有数据权限,请联系管理员");
        //如果当前用户没有最高数据权限只有代理商分权限
        if (!map.containsKey(CoreConfig.AUTH_DATA_ALL) && map.containsKey(CoreConfig.AUTH_DATA_AGENT)) {
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
        List<Hospital> hospitalList = findListByAid(String.valueOf(addVo.getAid()));
        if (hospitalList == null || hospitalList.size() <= 0) throw new ParamException("请选择正确的代理商");
        if (isExitsName(addVo.getAid(), addVo.getName()) > 0) throw new ParamException("医院名称已存在,请重新输入");
        Hospital hospital = hospitalVoToHospital(addVo, AddVo.class);
        hospital.setCountry(0);
        hospital.setProvince(addVo.getPid());
        hospital.setCity(addVo.getCid());
        hospital.setCrtId(uid);
        hospital.setEnable(22);
        hospital.setCrtTime(new Date());
        return hospitalMapper.insert(hospital);
    }

    @Override
    public List<SelectVO> getHospitalListByUid(String type, long uid)  {
        return hospitalMapper.getHospitalListByUid(type, uid);
    }

    @Override
    public List<SelectVO> getHospitalList(int uid, int aid, String name) {
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
