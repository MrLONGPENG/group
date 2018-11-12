package com.mujugroup.lock.service.impl;

import com.lveqia.cloud.common.objeck.to.LockTo;
import com.mujugroup.lock.mapper.LockFailMapper;
import com.mujugroup.lock.mapper.LockInfoMapper;
import com.mujugroup.lock.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "feignService")
public class FeignServiceImpl implements FeignService {
    private final LockInfoMapper lockInfoMapper;
    private final LockFailMapper lockFailMapper;

    @Autowired
    public FeignServiceImpl(LockInfoMapper lockInfoMapper, LockFailMapper lockFailMapper) {
        this.lockInfoMapper = lockInfoMapper;
        this.lockFailMapper = lockFailMapper;
    }


    @Override
    public LockTo getLockInfo(String bid) {
        return lockInfoMapper.getInfoByBid(bid);
    }

    @Override
    public List<String> getFailNameByDid(String did) {
        return lockFailMapper.getFailNameByDid(did);
    }
}
