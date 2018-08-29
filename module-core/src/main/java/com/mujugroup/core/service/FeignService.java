package com.mujugroup.core.service;


import java.util.Map;

public interface FeignService {

    Map<Integer,String> getHospitalByAid(String aid);
}
