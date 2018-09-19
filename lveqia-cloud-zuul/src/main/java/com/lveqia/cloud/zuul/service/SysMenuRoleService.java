package com.lveqia.cloud.zuul.service;

import java.util.List;

/**
 * @author leolaurel
 */
public interface SysMenuRoleService {

    int putMidToRid(int rid, int[] mid);

    List<Integer> getMidByRid(int rid);

    int delMidByRid(int rid);
}
