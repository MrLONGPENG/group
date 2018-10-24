package com.mujugroup.core.service;


import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.DBMap;
import com.mujugroup.core.objeck.vo.department.DepartmentVo;
import com.mujugroup.core.objeck.vo.department.ListVo;
import com.mujugroup.core.objeck.vo.department.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;

import java.util.List;


public interface DepartmentService {
    List<DBMap> findOidByHid(String hid);

    boolean insert(int uid, DepartmentVo departmentVo) throws ParamException, DataException;

    boolean update(int uid, PutVo departmentPutVo) throws ParamException, DataException;

    boolean delete(int uid, String id) throws ParamException, DataException;

    List<ListVo> findAll(int uid, int hid, String name) throws DataException;
}
