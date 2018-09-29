package com.mujugroup.core.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.core.mapper.AuthDataMapper;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.objeck.bo.HospitalBO;
import com.mujugroup.core.service.FeignService;
import com.mujugroup.core.service.HospitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("feignService")
public class FeignServiceImpl implements FeignService {

    private final HospitalService hospitalService;
    private final AuthDataMapper authDataMapper;
    private final Logger logger = LoggerFactory.getLogger(FeignServiceImpl.class);

    public FeignServiceImpl(HospitalService hospitalService, AuthDataMapper authDataMapper) {
        this.hospitalService = hospitalService;
        this.authDataMapper = authDataMapper;

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
        int[] ridArray = new int[authData.length];
        int[] typeArrary = new int[authData.length];
        if (authData != null && authData.length > 0) {
            String str;
            for (int i = 0; i < authData.length; i++) {
                //将关系ID放入关系数组中存储
                ridArray[i] = Integer.parseInt(authData[i].substring(3));
                //将关系类型加入到类型数组中
                str = authData[i].substring(0, 3);
                if (str.equalsIgnoreCase("AID")) {
                    typeArrary[i] = 1;
                } else if (str.equalsIgnoreCase("HID")) {
                    typeArrary[i] = 2;
                } else {
                    typeArrary[i] = 3;
                }
            }
        }
        return authDataMapper.addAuthData(uid,ridArray,typeArrary);

    }

    @Override
    public Map<String, String> getHospitalJson(String param) {
        logger.debug("getHospitalJson->{}", param);
        HashMap<String, String> hashMap =  new HashMap<>();
        String[] array = param.split(Constant.SIGN_SEMICOLON);
        Gson gson = new GsonBuilder().create();
        List<HospitalBO> list = hospitalService.getHospitalBoByIds(array);
        for (HospitalBO bo:list){
            hashMap.put(bo.getHid(), gson.toJson(bo));
        }
        return hashMap;
    }
}

