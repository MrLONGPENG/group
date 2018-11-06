package com.mujugroup.core.service;


import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.core.objeck.vo.SelectVo;
import com.mujugroup.core.objeck.vo.dictDepartment.AddVo;
import com.mujugroup.core.objeck.vo.dictDepartment.ListVo;
import com.mujugroup.core.objeck.vo.dictDepartment.PutVo;

import java.util.List;

/**
 * @author leolaurel
 */
public interface DictDepartmentService {
    boolean add(int uid, AddVo dictDepartmentAddVo) throws ParamException;

    boolean update(int uid, PutVo dictDepartmentPutVo) throws ParamException;

    boolean delete(String id) throws ParamException;

    List<SelectVo> getDictDepartmentList(String name);

    List<ListVo> findAll(String name);
}
