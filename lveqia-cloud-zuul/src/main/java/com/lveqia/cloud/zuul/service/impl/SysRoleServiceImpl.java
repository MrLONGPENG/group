package com.lveqia.cloud.zuul.service.impl;

import com.lveqia.cloud.common.exception.ExistException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.zuul.mapper.SysRoleMapper;
import com.lveqia.cloud.zuul.model.SysRole;
import com.lveqia.cloud.zuul.service.SysMenuRoleService;
import com.lveqia.cloud.zuul.service.SysRoleService;
import com.lveqia.cloud.zuul.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author leolaurel
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {


    private final SysRoleMapper sysRoleMapper;
    private final SysUserRoleService sysUserRoleService;
    private final SysMenuRoleService sysMenuRoleService;
    @Autowired
    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper, SysUserRoleService sysUserRoleService, SysMenuRoleService sysMenuRoleService) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysUserRoleService = sysUserRoleService;
        this.sysMenuRoleService = sysMenuRoleService;
    }

    @Override
    public List<SysRole> getRoleListByUid(long id) {
        return id == 1 ? sysRoleMapper.getRoleListByAdmin() : sysRoleMapper.getRoleListByUid(id);
    }

    @Override
    @Transactional
    public boolean addRole(long id, String name, String desc) throws ExistException, ParamException {
        String role = name;
        if(name.startsWith("ROLE_")){
            name = name.substring(5);
        }else{
            role = "ROLE_" + name;
        }
        if(!StringUtil.isLetter(name)) throw new ParamException("英文名需要全字母，请重新输入!");
        if(sysRoleMapper.exist(role, desc)) throw new ExistException("已存在相同的英文或中文名，请重新输入!");
        SysRole sysRole = getSysRole(role, desc);
        if(sysRoleMapper.insert(sysRole)){
            return sysUserRoleService.addUserRole(id, sysRole.getId());
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delRole(long id, int rid){
        boolean result = sysUserRoleService.delUserRole(id, rid);
        int userCount = sysUserRoleService.getUserCountByRid(rid);
        if(userCount == 0) {
            result |= sysMenuRoleService.delMidByRid(rid) >0 ;
            result |= sysRoleMapper.deleteById(rid);
        }
        return result;
    }

    /**
     * 构建Role对象
     */
    private SysRole getSysRole(String name, String desc) {
        SysRole sysRole = new SysRole();
        sysRole.setName("ROLE_"+name);
        sysRole.setDesc(desc);
        return sysRole;
    }
}
