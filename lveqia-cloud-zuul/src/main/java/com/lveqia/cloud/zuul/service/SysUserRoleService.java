package com.lveqia.cloud.zuul.service;

/**
 * @author leolaurel
 */
public interface SysUserRoleService {

    boolean addUserRole(long uid, Integer rid);

    int delUserRole(long uid, int rid);

    int delUserRoleByUid(int uid);

    int getUserCountByRid(int rid);

    int putRidToUid(int uid, int[] rid);
}
