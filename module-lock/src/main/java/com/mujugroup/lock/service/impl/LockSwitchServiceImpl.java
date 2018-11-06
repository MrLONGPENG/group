package com.mujugroup.lock.service.impl;

import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.lock.mapper.LockSwitchMapper;
import com.mujugroup.lock.model.LockSwitch;
import com.mujugroup.lock.service.LockSwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leolaurel
 */
@Service("lockSwitchService")
public class LockSwitchServiceImpl implements LockSwitchService {


    private final LockSwitchMapper lockSwitchMapper;


    @Autowired
    public LockSwitchServiceImpl(LockSwitchMapper lockSwitchMapper) {
        this.lockSwitchMapper = lockSwitchMapper;

    }

    @Override
    public boolean add(LockSwitch lockSwitch) {
        return lockSwitchMapper.insert(lockSwitch);
    }

    @Override
    public List<LockSwitch> getLockStatusList(String did, String bid) throws ParamException {
        if (StringUtil.isEmpty(did)&&StringUtil.isEmpty(bid))throw  new ParamException("请输入业务编号或锁编号进行查询!");
        return lockSwitchMapper.getLockStatusList(did,bid);
    }

}
