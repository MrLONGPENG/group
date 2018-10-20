package com.mujugroup.core.service;


import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.DBMap;
import com.mujugroup.core.objeck.vo.Department.DepartmentVo;
import com.mujugroup.core.objeck.vo.Department.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;

import java.util.List;


public interface DepartmentService {
    List<DBMap> findOidByHid(String hid);

    List<SelectVO> getListByHid(String hid);

    List<SelectVO> getListByHidOrName(String hid, String name);

    List<SelectVO> getDepartmentList(String name);

    boolean insert(DepartmentVo departmentVo) throws ParamException;

    boolean update(String id, PutVo departmentPutVo) throws ParamException;

    boolean delete(String id) throws ParamException;
}
