package com.mujugroup.core.service.impl;

import com.mujugroup.core.mapper.DepartmentMapper;
import com.mujugroup.core.model.Department;
import com.mujugroup.core.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("departmentService")
public class DepartmentServiceImpl  implements DepartmentService {
    private final DepartmentMapper departmentMapper;
    @Autowired
    public  DepartmentServiceImpl(DepartmentMapper departmentMapper){
        this.departmentMapper=departmentMapper;
    }

    @Override
    public Set<Integer> findOidByHid(String hid) {
        return departmentMapper.findOidByHid(hid);
    }
}
