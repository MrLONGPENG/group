package com.mujugroup.core.service.impl;

import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.model.Hospital;
import com.mujugroup.core.service.HospitalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService {


    private final HospitalMapper hospitalMapper;
    private final Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);
    @Autowired
    public HospitalServiceImpl(HospitalMapper hospitalMapper) {
        this.hospitalMapper = hospitalMapper;
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
}
