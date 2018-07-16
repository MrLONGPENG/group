package com.mujugroup.core.service.impl;

import com.mujugroup.core.mapper.HospitalMapper;
import com.mujugroup.core.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("hospitalService")
public class HospitalServiceImpl implements HospitalService {


    private final HospitalMapper hospitalMapper;

    @Autowired
    public HospitalServiceImpl(HospitalMapper hospitalMapper) {
        this.hospitalMapper = hospitalMapper;
    }

    @Override
    public String test() {
        return "hello world!";
    }
}
