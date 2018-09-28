package com.mujugroup.core.service;


import java.util.Map;
import java.util.Set;

public interface FeignService {

    Map<Integer,String> getHospitalByAid(String aid);

    Set<Integer> getHospitalByRegion(String pid, String cid);

    //int addAuthData(int uid,int[] ids,int[] types);
    int addAuthData(int uid,String[] authDatas);
}
