package com.mujugroup.core.service.impl;

import com.lveqia.cloud.common.objeck.DBMap;
import com.mujugroup.core.mapper.DepartmentMapper;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("departmentService")
public class DepartmentServiceImpl  implements DepartmentService {
    private final DepartmentMapper departmentMapper;
    @Autowired
    public  DepartmentServiceImpl(DepartmentMapper departmentMapper){
        this.departmentMapper=departmentMapper;
    }

    @Override
    public List<DBMap> findOidByHid(String hid) {
        return departmentMapper.findOidByHid(hid);
    }

    @Override
    public  List<SelectVO> getListByHid(String hid) {
        return departmentMapper.getListByHid(hid);
    }
}
