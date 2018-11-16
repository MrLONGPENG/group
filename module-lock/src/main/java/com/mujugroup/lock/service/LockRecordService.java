package com.mujugroup.lock.service;

import com.mujugroup.lock.model.LockRecord;

import java.util.List;

/**
 * @author leolaurel
 */
public interface LockRecordService {
    boolean add(LockRecord lockRecord);

    List<LockRecord> findByDid(String did, int limitCount);
}
