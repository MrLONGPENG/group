package com.mujugroup.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.config.CoreConfig;
import com.lveqia.cloud.common.objeck.DBMap;
import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.lveqia.cloud.common.objeck.vo.AuthVo;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.objeck.bo.HospitalBo;
import com.mujugroup.core.objeck.vo.SelectVo;
import com.mujugroup.core.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("feignService")
public class FeignServiceImpl implements FeignService {

    private final AgentService agentService;
    private final DeviceService deviceService;
    private final HospitalService hospitalService;
    private final AuthDataService authDataService;
    private final DepartmentService departmentService;
    private final Logger logger = LoggerFactory.getLogger(FeignServiceImpl.class);

    @Autowired
    public FeignServiceImpl(AgentService agentService, DeviceService deviceService, HospitalService hospitalService, AuthDataService authDataService
            , DepartmentService departmentService) {
        this.agentService = agentService;
        this.deviceService = deviceService;
        this.hospitalService = hospitalService;
        this.authDataService = authDataService;
        this.departmentService = departmentService;
    }

    @Override
    public Map<Integer, String> getHospitalByAid(String aid) {
        Map<Integer, String> map = new HashMap<>();
        List<Hospital> list = hospitalService.findListByAid(aid);
        for (Hospital hospital : list) {
            map.put(hospital.getId(), hospital.getName());
        }
        return map;
    }

    @Override
    public Set<Integer> getHospitalByRegion(String pid, String cid) {
        Set<Integer> set = new HashSet<>();
        List<Hospital> list = hospitalService.getHospitalByRegion(pid, cid);
        for (Hospital hospital : list) {
            set.add(hospital.getId());
        }
        return set;
    }

    @Override
    public int addAuthData(int uid, String[] authData) {
        return authDataService.addAuthData(uid, authData);

    }

    @Override
    public Map<String, String> getHospitalJson(String param) {
        logger.debug("getHospitalJson->{}", param);
        HashMap<String, String> hashMap = new HashMap<>();
        String[] array = param.split(Constant.SIGN_FEN_HAO);
        Gson gson = new GsonBuilder().create();
        List<HospitalBo> list = hospitalService.getHospitalBoByIds(array);
        for (HospitalBo bo : list) {
            hashMap.put(bo.getHid(), gson.toJson(bo));
        }
        return hashMap;
    }

    @Override
    public Map<String, String> getAuthData(int uid) {
        HashMap<String, String> hashMap = new HashMap<>();
        List<DBMap> list = authDataService.getAuthData(uid);
        list.forEach(dbMap -> dbMap.addTo(hashMap));
        return hashMap;
    }

    @Override
    public Map<Integer, String> findOidByHid(String hid) {
        HashMap<Integer, String> hashMap = new HashMap<>();
        List<DBMap> list = departmentService.findOidByHid(hid);
        for (DBMap map : list) {
            hashMap.put(Integer.parseInt(map.getKey()), map.getValue());
        }
        return hashMap;
    }

    @Override
    public String getHospitalName(int id) {
        return hospitalService.getHospitalName(id);
    }

    @Override
    public InfoTo getDeviceInfo(String did, String bid) {
        return deviceService.getDeviceInfo(did, bid);
    }

    @Override
    public PageInfo<SelectVo> getAuthLevel(AuthVo authVo) {
        List<SelectVo> list;
        PageHelper.startPage(authVo.getPageNum(), authVo.getPageSize());
        switch (authVo.getLevel()) {
            case CoreConfig.AUTH_DATA_AGENT:
                list = agentService.getAidByUid(authVo.getUid());
                break;
            case CoreConfig.AUTH_DATA_HOSPITAL:
                list = hospitalService.getHidByUid(authVo.getUid());
                break;
            case CoreConfig.AUTH_DATA_DEPARTMENT:
                list = departmentService.getOidByUid(authVo.getUid());
                break;
            default:
                list = new ArrayList<>();
        }
        return PageInfo.of(list);
    }

    @Override
    public List<InfoTo> getActivateInfoTo() {
        return deviceService.getActivateInfoTo();
    }
}

