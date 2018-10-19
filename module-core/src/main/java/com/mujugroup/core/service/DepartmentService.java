package com.mujugroup.core.service;


import com.lveqia.cloud.common.objeck.DBMap;
import com.mujugroup.core.objeck.vo.SelectVO;

import java.util.List;


public interface DepartmentService {
    List<DBMap> findOidByHid(String hid);

    List<SelectVO> getListByHid(String hid);
}
