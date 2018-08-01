package com.mujugroup.lock.controller;

import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.StringUtil;
import com.mujugroup.lock.model.LockInfo;
import com.mujugroup.lock.service.LockInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/info")
public class LockInfoController {

    private LockInfoService lockInfoService;
    private final Logger logger = LoggerFactory.getLogger(LockInfoController.class);
    @Autowired
    public LockInfoController(LockInfoService lockInfoService) {
        this.lockInfoService = lockInfoService;
    }

    @RequestMapping(value = "/query")
    public String query(String did){
        if(did == null) return ResultUtil.error(ResultUtil.CODE_PARAMETER_MISS);
        if(did.length()>19) return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT);
        LockInfo lockInfo = lockInfoService.getLockInfoByDid(did);
        if(lockInfo == null) return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        return ResultUtil.success(lockInfo);
    }
    @RequestMapping(value = "/getHardwareInfo", method = RequestMethod.POST)
    public Map<String, String> getHardwareInfo(String key) {
        logger.debug("lock-getHardwareInfo->{}", key);
        HashMap<String, String> hashMap =  new HashMap<>();
        String[] array = key.split(",");
        StringBuilder sb;
        LockInfo lockInfo;
        for (String bid: array) {
            lockInfo = lockInfoService.getLockInfoByDid(bid);
            if(lockInfo!=null){
                // BID;开锁状态;电池电量;最后上报时间;信号;温度;固件版本;硬件版本;充电电压;电池电压;充电电流
                sb = new StringBuilder(StringUtil.autoFillDid(lockInfo.getDid()));
                sb.append(";").append(lockInfo.getLockStatus()).append(";").append(lockInfo.getBatteryStat());
                sb.append(";").append(lockInfo.getLastRefresh().getTime()/1000).append(";").append(lockInfo.getCsq());
                sb.append(";").append(lockInfo.getTemp()).append(";").append(lockInfo.getFVersion());
                sb.append(";").append(lockInfo.getHVersion()).append(";").append(lockInfo.getCharge());
                sb.append(";").append(lockInfo.getVoltage()).append(";").append(lockInfo.getElectric());
                hashMap.put(bid, new String(sb));
            }
        }
        return hashMap;
    }
}
