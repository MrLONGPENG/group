package com.mujugroup.lock.service.impl;



import com.mujugroup.lock.mapper.LockDidMapper;
import com.mujugroup.lock.model.LockDid;
import com.mujugroup.lock.service.LockDidService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service("lockDidService")
public class LockDidServiceImpl implements LockDidService {

    private final LockDidMapper lockDidMapper;
    private final Logger logger = LoggerFactory.getLogger(LockDidServiceImpl.class);
    @Autowired
    public LockDidServiceImpl(LockDidMapper lockDidMapper) {
        this.lockDidMapper = lockDidMapper;
    }


    @Override
    @Cacheable(value = "lock_did_did", key = "#did", unless="#result == null")
    public LockDid getLockDidByDid(String did) {
        logger.debug("getLockDidByDid");
        return lockDidMapper.getLockDidByDid(did);
    }

    @Override
    @Cacheable(value = "lock_did_bid", key = "#bid", unless="#result == null")
    public LockDid getLockDidByBid(String bid) {
        logger.debug("getLockDidByBid");
        return lockDidMapper.getLockDidByBid(bid);
    }



}
