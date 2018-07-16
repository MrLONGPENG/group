package com.mujugroup.lock.service;


import com.mujugroup.lock.model.LockDid;

public interface LockDidService {
    LockDid getLockDidByDid(String did);

    LockDid getLockDidByBid(String bid);


}
