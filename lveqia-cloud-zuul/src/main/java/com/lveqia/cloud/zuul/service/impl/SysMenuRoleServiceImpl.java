package com.lveqia.cloud.zuul.service.impl;

import com.lveqia.cloud.zuul.mapper.SysMenuRoleMapper;
import com.lveqia.cloud.zuul.service.SysMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author leolaurel
 */
@Service("sysMenuRoleService")
public class SysMenuRoleServiceImpl implements SysMenuRoleService {


    private final SysMenuRoleMapper sysMenuRoleMapper;

    @Autowired
    public SysMenuRoleServiceImpl(SysMenuRoleMapper sysMenuRoleMapper) {
        this.sysMenuRoleMapper = sysMenuRoleMapper;
    }

    @Override
    public String test() {
        return "hello world!";
    }
}
