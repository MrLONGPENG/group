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

    @Autowired
    public LockInfoServiceImpl(LockInfoMapper lockInfoMapper) {
        this.lockInfoMapper = lockInfoMapper;
    }


    @Override
    @Cacheable(value = "lock_info_lock_id", key = "#bid", unless="#result == null")
    public LockInfo getLockInfoByBid(String bid) {
        return lockInfoMapper.getLockInfoByBid(bid);
    }

    @Override
    public LockInfo insert(LockInfo lockInfo) {
        return lockInfoMapper.insert(lockInfo)?lockInfo:null;
    }

    @Override
    @CachePut(value = "lock_info_lock_id", key = "#lockInfo.lockId", unless="#result == null")
    public LockInfo update(LockInfo lockInfo) {
        return lockInfoMapper.update(lockInfo)?lockInfo:null;
    }

}
