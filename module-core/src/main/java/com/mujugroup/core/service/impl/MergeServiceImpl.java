package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.core.mapper.DepartmentMapper;
import com.mujugroup.core.mapper.DeviceMapper;
import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.model.Department;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.objeck.bo.TreeBo;
import com.mujugroup.core.service.AgentService;
import com.mujugroup.core.service.AuthDataService;
import com.mujugroup.core.service.MergeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("mergeService")
public class MergeServiceImpl implements MergeService {
    private final AgentService agentService;
    private final DeviceMapper deviceMapper;
    private final HospitalMapper hospitalMapper;
    private final DepartmentMapper departmentMapper;
    private final AuthDataService authDataService;
    private final Logger logger = LoggerFactory.getLogger(MergeServiceImpl.class);

    public MergeServiceImpl(AgentService agentService, DeviceMapper deviceMapper, HospitalMapper hospitalMapper
            , DepartmentMapper departmentMapper, AuthDataService authDataService) {
        this.agentService = agentService;
        this.deviceMapper = deviceMapper;
        this.hospitalMapper = hospitalMapper;
        this.departmentMapper = departmentMapper;
        this.authDataService = authDataService;
    }


    @Override
    public Map<String, String> getHidMapByAid(String param) {
        logger.debug("getHidMapByAid->{}", param);
        Map<String, String> map = new HashMap<>();
        List<Hospital> list = hospitalMapper.findListByAid(param);
        for (Hospital hospital : list) {
            map.put(String.valueOf(hospital.getId()), hospital.getName());
        }
        return map;
    }

    @Override
    public Map<String, String> getOidMapByHid(String param) {
        logger.debug("getOidMapByHid->{}", param);
        Map<String, String> map = new HashMap<>();
        List<Department> list = departmentMapper.findListByHid(param.split(Constant.SIGN_AND)[1]);
        for (Department department : list) {
            map.put(String.valueOf(department.getId()), department.getName());
        }
        return map;
    }

    @Override
    public Map<String, String> getNewlyActiveCount(String param) {
        logger.debug("getNewlyActiveCount->{}", param);
        String[] params = param.split(Constant.SIGN_AND);
        HashMap<String, String> hashMap = new HashMap<>();
        List<DBMap> list = null;
        if ("1,2,3".contains(params[3])) {
            list = deviceMapper.getActiveByGroup(params[0], params[1], params[2], params[3], params[4], params[5]);
        }
        if (list != null) list.forEach(dbMap -> dbMap.addTo(hashMap));
        return hashMap;
    }

    @Override
    public Map<String, String> getTotalActiveCount(String param) {
        logger.debug("getTotalActiveCount->{}", param);
        HashMap<String, String> hashMap = new HashMap<>();
        String[] array = param.split(Constant.SIGN_FEN_HAO);
        for (String key : array) {
            if (StringUtil.isEmpty(key)) continue;
            String[] keys = key.split(Constant.SIGN_AND);
            int activeCount = deviceMapper.getActiveCount(keys[0], StringUtil.formatIds(keys[1]), keys[2]
                    , Constant.DIGIT_ZERO, keys[3]);
            activeCount += deviceMapper.getActiveRemoveCount(keys[0], StringUtil.formatIds(keys[1]), keys[2]
                    , Constant.DIGIT_ZERO, keys[3]);
            hashMap.put(key, String.valueOf(activeCount));
        }
        return hashMap;
    }

    @Override
    public Map<String, String> getAgentById(String param) {
        logger.debug("getAgentById->{}", param);
        HashMap<String, String> hashMap = new HashMap<>();
        String[] array = param.split(Constant.SIGN_FEN_HAO);
        for (String key : array) {
            checkHasMap(hashMap, key,  agentService.getAgentName(key));
        }
        return hashMap;
    }

    @Override
    public Map<String, String> getHospitalById(String param) {
        logger.debug("getHospitalById->{}", param);
        HashMap<String, String> hashMap = new HashMap<>();
        String[] array = param.split(Constant.SIGN_FEN_HAO);
        for (String key : array) {
            checkHasMap(hashMap, key, hospitalMapper.getHospitalById(key));
        }
        return hashMap;
    }

    @Override
    public Map<String, String> getProvinceByHid(String param) {
        logger.debug("getProvinceByHid->{}", param);
        HashMap<String, String> hashMap = new HashMap<>();
        String[] array = param.split(Constant.SIGN_FEN_HAO);
        for (String key : array) {
            checkHasMap(hashMap, key, hospitalMapper.getProvinceByHid(key));
        }
        return hashMap;
    }

    @Override
    public Map<String, String> getCityByHid(String param) {
        logger.debug("getCityByHid->{}", param);
        HashMap<String, String> hashMap = new HashMap<>();
        String[] array = param.split(Constant.SIGN_FEN_HAO);
        for (String key : array) {
            checkHasMap(hashMap, key,  hospitalMapper.getCityByHid(key));
        }
        return hashMap;
    }

    @Override
    public Map<String, String> getDepartmentById(String param) {
        logger.debug("getDepartmentById->{}", param);
        HashMap<String, String> hashMap = new HashMap<>();
        String[] array = param.split(Constant.SIGN_FEN_HAO);
        for (String key : array) {
            checkHasMap(hashMap, key,  departmentMapper.getDepartmentNameById(key));
        }
        return hashMap;
    }

    @Override
    public Map<String, String> getBedInfoByDid(String param) {
        logger.debug("getBedInfoByDid->{}", param);
        HashMap<String, String> hashMap = new HashMap<>();
        String[] array = param.split(Constant.SIGN_FEN_HAO);
        for (String did : array) {
            checkHasMap(hashMap, did, deviceMapper.getBedInfoByDid(did));
        }
        return hashMap;
    }

    private void checkHasMap(HashMap<String, String> hashMap, String key, String value) {
        if (StringUtil.isEmpty(key) || StringUtil.isEmpty(value) || key.equals(Constant.DIGIT_ZERO)) return;
        hashMap.put(key, value);
    }

    @Override
    public Map<String, String> getAuthTree(String param) {
        logger.debug("getAuthTree {}", param);
        HashMap<String, String> map = new HashMap<>();
        String[] array = param.split(Constant.SIGN_FEN_HAO);
        for (String key : array) {
            if (StringUtil.isEmpty(key)) continue;
            map.put(key, getAuthTreeById(key));
        }
        return map;
    }

    /**
     * 根据AID或OID查询树结构
     */
    private String getAuthTreeById(String key) {
        List<TreeBo> list = new ArrayList<>();
        if (key.startsWith("AID")) list = authDataService.getAuthTreeByAid(key.substring(3));
        // TODO 暂时只处理到医院，如需要到科室，可放出下面
        //if(key.startsWith("HID")) list = authDataService.getAuthTreeByHid(key.substring(3));
        return authDataService.toJsonString(list);
    }


}
