package com.mujugroup.core.service;


import com.mujugroup.core.model.Department;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FeignService {

    Map<Integer, String> getHospitalByAid(String aid);

    Set<Integer> getHospitalByRegion(String pid, String cid);

    int addAuthData(int uid, String[] authData);

    Map<String, String> getHospitalJson(String param);

    Map<String, String> getAuthData(int uid);

    //通过医院ID获取所属科室ID
   Map<Integer,String> findOidByHid(String hid);
   //通过医院ID获取医院名称
   String getHospitalName(int id);
}
