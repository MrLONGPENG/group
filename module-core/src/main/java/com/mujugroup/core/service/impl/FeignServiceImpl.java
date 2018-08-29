package com.mujugroup.core.service.impl;

import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.service.FeignService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("feignService")
public class FeignServiceImpl implements FeignService {

    private final HospitalMapper hospitalMapper;

    //private final Logger logger = LoggerFactory.getLogger(FeignServiceImpl.class);

    public FeignServiceImpl(HospitalMapper hospitalMapper) {
        this.hospitalMapper = hospitalMapper;

    }


    @Override
    public Map<Integer, String> getHospitalByAid(String aid) {
        Map<Integer, String> map = new HashMap<>();
        List<Hospital> list = hospitalMapper.findListByAid(aid);
        for (Hospital hospital: list){
            map.put(hospital.getId(), hospital.getName());
        }
        return map;
    }
}
