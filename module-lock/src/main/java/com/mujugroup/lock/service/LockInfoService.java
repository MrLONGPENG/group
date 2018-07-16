package com.mujugroup.lock.service;


import com.mujugroup.lock.model.LockInfo;

public interface LockInfoService {

    LockInfo getLockInfoByDid(String did);

    LockInfo insert(LockInfo lockInfo);

    LockInfo update(LockInfo lockInfo);
}
