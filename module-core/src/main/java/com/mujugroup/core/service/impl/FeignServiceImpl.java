package com.mujugroup.core.service.impl;

import com.mujugroup.core.mapper.AuthDataMapper;
import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.service.FeignService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("feignService")
public class FeignServiceImpl implements FeignService {

    private final HospitalMapper hospitalMapper;
    private final AuthDataMapper authDataMapper;

    //private final Logger logger = LoggerFactory.getLogger(FeignServiceImpl.class);

    public FeignServiceImpl(HospitalMapper hospitalMapper, AuthDataMapper authDataMapper) {
        this.hospitalMapper = hospitalMapper;
        this.authDataMapper = authDataMapper;

    }


    @Override
    public Map<Integer, String> getHospitalByAid(String aid) {
        Map<Integer, String> map = new HashMap<>();
        List<Hospital> list = hospitalMapper.findListByAid(aid);
        for (Hospital hospital : list) {
            map.put(hospital.getId(), hospital.getName());
        }
        return map;
    }

    @Override
    public Set<Integer> getHospitalByRegion(String pid, String cid) {
        Set<Integer> set = new HashSet<>();
        List<Hospital> list = hospitalMapper.getHospitalByRegion(pid, cid);
        for (Hospital hospital : list) {
            set.add(hospital.getId());
        }
        return set;
    }

    /*@Override
    public int addAuthData(int uid, int[] ids, int[] types) {
        return  authDataMapper.addAuthData(uid,ids,types);
    }*/

    @Override
    public int addAuthData(int uid, String[] authDatas) {
        int[] ridArray = new int[authDatas.length];
        int[] typeArrary = new int[authDatas.length];
        if (authDatas != null && authDatas.length > 0) {
            String str=null;
            for (int i = 0; i < authDatas.length; i++) {
                //将关系ID放入关系数组中存储
                ridArray[i] = Integer.parseInt(authDatas[i].substring(3));
                //将关系类型加入到类型数组中
                str=authDatas[i].substring(0, 3);
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
}

