package com.mujugroup.lock.service;


import com.mujugroup.lock.model.LockRecord;

/**
 * @author leolaurel
 */
public interface LockRecordService {
  boolean add(LockRecord lockRecord);
}
