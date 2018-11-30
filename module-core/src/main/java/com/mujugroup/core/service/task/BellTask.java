package com.mujugroup.core.service.task;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.objeck.to.UptimeTo;
import com.lveqia.cloud.common.util.DateUtil;
import com.mujugroup.core.model.Device;
import com.mujugroup.core.service.DeviceService;
import com.mujugroup.core.service.feign.ModuleLockService;
import com.mujugroup.core.service.feign.ModuleWxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class BellTask {

    private final static int DELAY_TIME = 15 * 60;    // 到时延迟十五分钟
    private final static int INTERVAL_TIME =  10 * 60; // 十分钟
    private final DeviceService deviceService;
    private final ModuleWxService moduleWxService;
    private final ModuleLockService moduleLockService;
    private final Map<String, UptimeTo> uptimeToMap = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(BellTask.class);

    @Value("${spring.profiles.active}")
    private String model;

    @Autowired
    public BellTask(DeviceService deviceService, ModuleWxService moduleWxService, ModuleLockService moduleLockService) {
        this.deviceService = deviceService;
        this.moduleWxService = moduleWxService;
        this.moduleLockService = moduleLockService;
    }

    @Scheduled(cron = "0 0/5 6-21 * * *")
    public void onCron() {
        logger.debug("BellTask date: {}", new Date());
        uptimeToMap.clear();
        onDevice(DateUtil.getTimesNoDate(), 1, 5);
    }

    private void onDevice(int currTime, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Device> list = deviceService.findListByStatus(Device.TYPE_ENABLE);
        if (list == null) return;
        PageInfo pageInfo = PageInfo.of(list);
        StringBuilder key = new StringBuilder();
        for (Device device : list) {
            if (device.getBell() == 0) continue; //TODO 临时增加控制是否开启bell
            // 只在设置的时间内可以警报，其他未知情况全部跳过
            if (isNeedBell(currTime, device.getAgentId(), device.getHospitalId(), device.getDepart(),device.getDid())) {
                key.append(device.getBid()).append(Constant.SIGN_FEN_HAO);
            }
        }
        if (key.length() > 0) {
            String[] array;
            Map<String, String> map = moduleLockService.getHardwareInfo(key.substring(0, key.length() - 1));
            for (String info : map.values()) {
                logger.debug(info);
                array = info.split(Constant.SIGN_FEN_HAO);
                if (array.length > 2 && Constant.LOCK_OPEN.equals(array[1])) {
                    moduleLockService.beep(array[0]);
                }
            }
        }

        if (pageInfo.getPages() > pageNum) {
            onDevice(currTime, pageNum + 1, pageSize);
        }
    }

    /**
     * 只有在非开锁情况下需要警报，未知情况下不报
     */
    private boolean isNeedBell(int currTime, Integer agentId, Integer hospitalId, Integer depart, String did) {
        UptimeTo uptimeTo = getUptimeTo(agentId, hospitalId, depart);
        if(uptimeTo!=null && uptimeTo.isNoonTime(currTime)) return false; // 午休时间不响铃
        boolean isTrue = false, isTime = false;
        if (uptimeTo!=null) {
            isTrue =  uptimeTo.isUsingTime(currTime);
            isTime = !isTrue && (currTime - uptimeTo.getStopTime() - DELAY_TIME) % INTERVAL_TIME == 0;
        }
        logger.debug("did: isTrue=>{} isTime=>{}", isTrue, isTime);
        return isTime || (isTrue && getUsingCount(did, System.currentTimeMillis()/1000) <= 0);
    }

    private int getUsingCount(String did, long time) {
        return moduleWxService.getCountByUsingDid(did, time);
    }

    private UptimeTo getUptimeTo(int agentId, int hospitalId, int depart) {
        String key = agentId + "-" + hospitalId + "-" + depart;
        if (uptimeToMap.containsKey(key)) return uptimeToMap.get(key);
        UptimeTo uptimeTo =  moduleWxService.getUptimeTo(agentId, hospitalId, depart);
        uptimeToMap.put(key, uptimeTo);
        return uptimeTo;
    }

}
