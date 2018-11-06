package com.mujugroup.core.service;


import com.lveqia.cloud.common.exception.DataException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.DBMap;
import com.mujugroup.core.objeck.vo.department.AddVo;
import com.mujugroup.core.objeck.vo.department.ListVo;
import com.mujugroup.core.objeck.vo.department.PutVo;
import com.mujugroup.core.objeck.vo.SelectVo;


import java.util.List;


public interface DepartmentService {
    List<DBMap> findOidByHid(String hid);

    boolean insert(int uid, AddVo departmentVo) throws ParamException, DataException;

    boolean update(int uid, PutVo departmentPutVo) throws ParamException, DataException;

    boolean delete(int uid, String id) throws ParamException, DataException;

    List<ListVo> findAll(String hid, String name,String status) throws DataException;

    List<SelectVo> getSelectList(int uid, int hid, String name) throws DataException;

    String checkUserData(int uid, String hid) throws DataException;
}
