package com.mujugroup.lock.service.impl;

import com.mujugroup.lock.mapper.LockInfoMapper;
import com.mujugroup.lock.model.LockInfo;
import com.mujugroup.lock.service.DeviceService;
import com.mujugroup.lock.service.LockInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service("lockInfoService")
public class LockInfoServiceImpl implements LockInfoService {


    private final LockInfoMapper lockInfoMapper;
    private final DeviceService deviceService;

    @Autowired
    public LockInfoServiceImpl(LockInfoMapper lockInfoMapper, DeviceService deviceService) {
        this.lockInfoMapper = lockInfoMapper;
        this.deviceService = deviceService;
    }


    @Override
    @Cacheable(value = "lock_info_did", key = "#did", unless="#result == null")
    public LockInfo getLockInfoByDid(String did) {
        return lockInfoMapper.getLockInfoByDid(did);
    }

    @Override
    public LockInfo insert(LockInfo lockInfo) {
        return lockInfoMapper.insert(lockInfo)?lockInfo:null;
    }

    @Override
    @CachePut(value = "lock_info_did", key = "#lockInfo.did", unless="#result == null")
    public LockInfo update(LockInfo lockInfo) {
        return lockInfoMapper.update(lockInfo)?lockInfo:null;
    }

}
