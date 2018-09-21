package com.lveqia.cloud.zuul.service;

import com.lveqia.cloud.common.exception.ExistException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.zuul.model.SysRole;

import java.util.List;

/**
 * @author leolaurel
 */
public interface SysRoleService {

    List<SysRole> getRoleListByUid(long id);

    List<SysRole> getRoleMenuByUid(long id);

    boolean addRole(long id, String name, String desc) throws ExistException, ParamException;

    boolean delRole(long id, int rid);

}
