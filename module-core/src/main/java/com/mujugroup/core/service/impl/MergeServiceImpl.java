package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.util.Constant;
import com.lveqia.cloud.common.util.DBMap;
import com.mujugroup.core.mapper.AgentMapper;
import com.mujugroup.core.mapper.DepartmentMapper;
import com.mujugroup.core.mapper.DeviceMapper;
import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.model.Department;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.service.MergeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("mergeService")
public class MergeServiceImpl implements MergeService {
    private final AgentMapper agentMapper;
    private final DeviceMapper deviceMapper;
    private final HospitalMapper hospitalMapper;
    private final DepartmentMapper departmentMapper;
    ;
    private final Logger logger = LoggerFactory.getLogger(MergeServiceImpl.class);

    public MergeServiceImpl(AgentMapper agentMapper, DeviceMapper deviceMapper, HospitalMapper hospitalMapper
            , DepartmentMapper departmentMapper) {
        this.agentMapper = agentMapper;
        this.deviceMapper = deviceMapper;
        this.hospitalMapper = hospitalMapper;
        this.departmentMapper = departmentMapper;
    }


    @Override
    public Map<String, String> getHidMapByAid(String param) {
        logger.debug("getHidMapByAid->{}", param);
        Map<String,String> map = new HashMap<>();
        List<Hospital> list =  hospitalMapper.findListByAid(param);
        for (Hospital hospital: list) {
            map.put(String.valueOf(hospital.getId()), hospital.getName());
        };
        return map;
    }

    @Override
    public Map<String, String> getOidMapByHid(String param) {
        logger.debug("getOidMapByHid->{}", param);
        Map<String,String> map = new HashMap<>();
        List<Department> list =  departmentMapper.findListByHid(param.split(Constant.SIGN_COMMA)[1]);
        for (Department department: list) {
            map.put(String.valueOf(department.getId()), department.getName());
        }
        return map;
    }

    @Override
    public Map<String, String> getNewlyActiveCount(String param) {
        logger.debug("getNewlyActiveCount->{}", param);
        String[] params = param.split(Constant.SIGN_COMMA);
        HashMap<String, String> hashMap =  new HashMap<>();
        List<DBMap> list = null;
        if("1,2,3".contains(params[3])){
            list = deviceMapper.getActiveByGroup(params[0], params[1], params[2], params[3], params[4], params[5]);
        }
        if(list!=null) list.forEach(dbMap -> dbMap.addTo(hashMap));
        return hashMap;
    }

    @Override
    public Map<String, String> getTotalActiveCount(String param) {
        logger.debug("getTotalActiveCount->{}", param);
        HashMap<String, String> hashMap =  new HashMap<>();
        String[] array = param.split(Constant.SIGN_SEMICOLON);
        for (String key:array){
            String[] keys = key.split(Constant.SIGN_COMMA);
            hashMap.put(key, deviceMapper.getActiveCount(keys[0], keys[1], keys[2], Constant.DIGIT_ZERO, keys[3]));
        }
        return hashMap;
    }

    @Override
    public Map<String, String> getAgentById(String param) {
        logger.debug("getAgentById->{}", param);
        HashMap<String, String> hashMap =  new HashMap<>();
        String[] array = param.split(Constant.SIGN_SEMICOLON);
        for (String key:array){
            hashMap.put(key, agentMapper.findById(Integer.parseInt(key)).getName());
        }
        return hashMap;
    }

    @Override
    public Map<String, String> getHospitalById(String param) {
        logger.debug("getHospitalById->{}", param);
        HashMap<String, String> hashMap =  new HashMap<>();
        String[] array = param.split(Constant.SIGN_SEMICOLON);
        for (String key:array){
            hashMap.put(key, hospitalMapper.findById(Integer.parseInt(key)).getName());
        }
        return hashMap;
    }

    @Override
    public Map<String, String> getDepartmentById(String param) {
        logger.debug("getDepartmentById->{}", param);
        HashMap<String, String> hashMap =  new HashMap<>();
        String[] array = param.split(Constant.SIGN_SEMICOLON);
        for (String key:array){
            hashMap.put(key, departmentMapper.getDepartmentNameById(key));
        }
        return hashMap;
    }

    @Override
    public Map<String, String> getBedInfoByDid(String param) {
        logger.debug("getBedInfoByDid->{}", param);
        HashMap<String, String> hashMap =  new HashMap<>();
        String[] array = param.split(Constant.SIGN_SEMICOLON);
        for (String did:array){
            hashMap.put(did, deviceMapper.getBedInfoByDid(did));
        }
        return hashMap;
    }
}
