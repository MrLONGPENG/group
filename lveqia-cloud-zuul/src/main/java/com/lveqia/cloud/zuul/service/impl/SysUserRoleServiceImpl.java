package com.lveqia.cloud.zuul.service.impl;

import com.lveqia.cloud.zuul.mapper.SysUserRoleMapper;
import com.lveqia.cloud.zuul.model.SysUserRole;
import com.lveqia.cloud.zuul.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author leolaurel
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {


    private final SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    public SysUserRoleServiceImpl(SysUserRoleMapper sysUserRoleMapper) {
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    @Override
    public boolean addUserRole(long uid, Integer rid) {
        return sysUserRoleMapper.insert(getUserRole(uid, rid));
    }

    /**
     * 根据用户ID和角色ID删除关联数据
     */
    @Override
    public int delUserRole(long uid, int rid) {
        return sysUserRoleMapper.delUserRole(uid, rid);
    }

    /**
     * 根据用户ID删除关联数据
     */
    @Override
    public int delUserRoleByUid(int uid) {
        return sysUserRoleMapper.delUserRoleByUid(uid);
    }

    /**
     * 查询当前角色有有多少用户使用
     */
    @Override
    public int getUserCountByRid(int rid) {
        return sysUserRoleMapper.getUserCountByRid(rid);
    }

    /**
     * 构建对象
     */
    private SysUserRole getUserRole(long uid, Integer rid) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUid((int) uid);
        sysUserRole.setRid(rid);
        return sysUserRole;
    }
}
