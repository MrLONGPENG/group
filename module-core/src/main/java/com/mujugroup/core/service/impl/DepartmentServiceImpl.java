package com.mujugroup.core.service.impl;

import com.mujugroup.core.mapper.DepartmentMapper;
import com.mujugroup.core.model.Department;
import com.mujugroup.core.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {


    private final DepartmentMapper departmentMapper;
    private final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    @Autowired
    public DepartmentServiceImpl(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }


    @Override
    public Map<String, String> getDepartmentMapByHid(String param) {
        logger.debug("getDepartmentMapByHid->{}", param);
        Map<String,String> map = new HashMap<>();
        List<Department> list =  departmentMapper.findListByHid(param.split(",")[1]);
        for (Department department: list) {
            map.put(String.valueOf(department.getId()), department.getName());
        }
        return map;
    }
}
