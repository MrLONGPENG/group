package com.mujugroup.core.service.impl;

import com.mujugroup.core.mapper.DepartmentMapper;
import com.mujugroup.core.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {


    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    @Override
    public String test() {
        return "hello world!";
    }
}
