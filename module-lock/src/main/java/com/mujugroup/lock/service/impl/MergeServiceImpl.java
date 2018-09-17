package com.mujugroup.lock.service.impl;

import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.lock.model.LockInfo;
import com.mujugroup.lock.service.LockInfoService;
import com.mujugroup.lock.service.MergeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("mergeService")
public class MergeServiceImpl implements MergeService {

    private final LockInfoService lockInfoService;
    private final Logger logger = LoggerFactory.getLogger(MergeServiceImpl.class);

    @Autowired
    public MergeServiceImpl(LockInfoService lockInfoService) {
        this.lockInfoService = lockInfoService;
    }

    @Override
    public Map<String, String> getHardwareInfo(String param) {
        logger.debug("getHardwareInfo-->{}", param);
        Map<String,String> map = new HashMap<>();
        String[] array = param.split(Constant.SIGN_SEMICOLON);
        StringBuilder sb;
        LockInfo lockInfo;
        for (String bid: array) {
            lockInfo = lockInfoService.getLockInfoByDid(bid);
            if(lockInfo!=null){
                // BID;开锁状态;电池电量;最后上报时间;信号;温度;固件版本;硬件版本;充电电压;电池电压;充电电流
                sb = new StringBuilder(StringUtil.autoFillDid(lockInfo.getDid()));
                sb.append(Constant.SIGN_SEMICOLON).append(lockInfo.getLockStatus());
                sb.append(Constant.SIGN_SEMICOLON).append(lockInfo.getBatteryStat());
                sb.append(Constant.SIGN_SEMICOLON).append(lockInfo.getLastRefresh().getTime()/1000);
                sb.append(Constant.SIGN_SEMICOLON).append(lockInfo.getCsq());
                sb.append(Constant.SIGN_SEMICOLON).append(lockInfo.getTemp());
                sb.append(Constant.SIGN_SEMICOLON).append(lockInfo.getFVersion());
                sb.append(Constant.SIGN_SEMICOLON).append(lockInfo.getHVersion());
                sb.append(Constant.SIGN_SEMICOLON).append(lockInfo.getCharge());
                sb.append(Constant.SIGN_SEMICOLON).append(lockInfo.getVoltage());
                sb.append(Constant.SIGN_SEMICOLON).append(lockInfo.getElectric());
                map.put(bid, new String(sb));
            }
        }
        return map;
    }
}
