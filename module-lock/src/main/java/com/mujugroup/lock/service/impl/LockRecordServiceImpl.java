package com.mujugroup.lock.service.impl;

import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.lock.mapper.LockRecordMapper;
import com.mujugroup.lock.model.LockRecord;
import com.mujugroup.lock.objeck.vo.record.ListVo;
import com.mujugroup.lock.service.LockRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leolaurel
 */
@Service("lockRecordService")
public class LockRecordServiceImpl implements LockRecordService {


    private final LockRecordMapper lockRecordMapper;

    @Autowired
    public LockRecordServiceImpl(LockRecordMapper lockRecordMapper) {
        this.lockRecordMapper = lockRecordMapper;
    }


    @Override
    public boolean add(LockRecord lockRecord) {
        return lockRecordMapper.insert(lockRecord);
    }

    @Override
    public List<LockRecord> findByDid(String did, int limitCount) {
        return lockRecordMapper.findByDid(did, limitCount);
    }

    @Override
    public List<ListVo> getRecordList(ListVo listVo) {
        return lockRecordMapper.getRecordList(listVo.getDid(), listVo.getLockId() == null ? "" : listVo.getLockId().toString()
                , listVo.getStartTime()
                , listVo.getEndTime()
                , listVo.getChargeStart()
                , listVo.getChargeEnd());
    }
}
