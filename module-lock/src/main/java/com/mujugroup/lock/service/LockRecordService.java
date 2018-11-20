package com.mujugroup.lock.service;

import com.mujugroup.lock.model.LockRecord;
import com.mujugroup.lock.objeck.vo.record.ListVo;

import java.util.List;

/**
 * @author leolaurel
 */
public interface LockRecordService {
    boolean add(LockRecord lockRecord);

    List<LockRecord> findByDid(String did, int limitCount);

    List<ListVo> getRecordList(ListVo listVo);
}
