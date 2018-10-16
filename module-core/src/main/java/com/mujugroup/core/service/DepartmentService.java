package com.mujugroup.core.service;


import com.lveqia.cloud.common.objeck.DBMap;

import java.util.List;


public interface DepartmentService  {
    List<DBMap> findOidByHid(String hid);
}
