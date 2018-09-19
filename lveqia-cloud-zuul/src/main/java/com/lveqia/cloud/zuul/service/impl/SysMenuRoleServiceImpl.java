package com.lveqia.cloud.zuul.service.impl;

import com.lveqia.cloud.zuul.controller.SysMenuController;
import com.lveqia.cloud.zuul.mapper.SysMenuRoleMapper;
import com.lveqia.cloud.zuul.service.SysMenuRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author leolaurel
 */
@Service("sysMenuRoleService")
public class SysMenuRoleServiceImpl implements SysMenuRoleService {


    private final SysMenuRoleMapper sysMenuRoleMapper;
    private final Logger logger = LoggerFactory.getLogger(SysMenuRoleServiceImpl.class);
    @Autowired
    public SysMenuRoleServiceImpl(SysMenuRoleMapper sysMenuRoleMapper) {
        this.sysMenuRoleMapper = sysMenuRoleMapper;
    }


    @Override
    @Transactional
    public int putMidToRid(int rid, int[] mid) {
        int count = sysMenuRoleMapper.delMidByRid(rid);
        logger.debug("putMidToRid del count:{}", count);
        if(mid.length == 0) return 0;
        return sysMenuRoleMapper.addMidRid(rid, mid);
    }

    @Override
    public List<Integer> getMidByRid(int rid) {
        return sysMenuRoleMapper.getMidByRid(rid);
    }

    @Override
    public int delMidByRid(int rid) {
        return sysMenuRoleMapper.delMidByRid(rid);
    }
}
