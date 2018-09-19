package com.lveqia.cloud.zuul.service;

/**
 * @author leolaurel
 */
public interface SysUserRoleService {

    boolean addUserRole(long uid, Integer rid);

    boolean delUserRole(long uid, int rid);

    int getUserCountByRid(int rid);


}
