package com.mujugroup.lock.service;


import com.lveqia.cloud.common.exception.ParamException;
import com.mujugroup.lock.model.LockRecord;

import java.util.List;

/**
 * @author leolaurel
 */
public interface LockRecordService {
    boolean add(LockRecord lockRecord);

    List<LockRecord> getLockStatusList(String did,String bid) throws ParamException;
}
