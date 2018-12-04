package com.mujugroup.lock.service.impl;

import com.lveqia.cloud.common.objeck.to.DataTo;
import com.lveqia.cloud.common.objeck.to.LockTo;
import com.mujugroup.lock.mapper.LockInfoMapper;
import com.mujugroup.lock.service.DeviceService;
import com.mujugroup.lock.service.FeignService;
import com.mujugroup.lock.service.LockFailService;
import com.mujugroup.lock.service.LockSwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service(value = "feignService")
public class FeignServiceImpl implements FeignService {

    private final LockInfoMapper lockInfoMapper;
    private final DeviceService deviceService;
    private final LockFailService lockFailService;
    private final LockSwitchService lockSwitchService;

    @Autowired
    public FeignServiceImpl(LockInfoMapper lockInfoMapper, DeviceService deviceService, LockFailService lockFailService, LockSwitchService lockSwitchService) {
        this.lockInfoMapper = lockInfoMapper;
        this.deviceService = deviceService;
        this.lockFailService = lockFailService;
        this.lockSwitchService = lockSwitchService;
    }


    @Override
    public String unlock(String did) {
        return deviceService.remoteCall(0, did);
    }

    @Override
    public String beep(String did) {
        return deviceService.remoteCall(2, did);
    }

    @Override
    public LockTo getLockInfo(String did) {
        LockTo lockTo = lockInfoMapper.getInfoByDid(did);
        if (lockTo != null) {
            lockTo.setDictName(getFailNameByDid(did));
        }
        return lockTo;
    }

    @Override
    public String getLockStatus(String did) {
        return lockInfoMapper.getLockStatus(did);
    }

    @Override
    public DataTo getRecordByDidAndLastRefresh(long did, long lastRefresh) {
        return lockSwitchService.getRecordByDidAndLastRefresh(did, lastRefresh);
    }

    @Override
    public List<DataTo> getFailRecordList() {
        return lockFailService.getFailRecordList();
    }

    @Override
    public List<String> getFailNameByDid(String did) {
        return lockFailService.getFailNameByDid(did);
    }
}
