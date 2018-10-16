package com.mujugroup.core.service.task;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lveqia.cloud.common.config.Constant;
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
    private final Map<String, Uptime> uptimeMap = new HashMap<>();
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
        uptimeMap.clear();
        onDevice(DateUtil.getTimesNoDate(), 1, 5);
    }

    private void onDevice(int currTime, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Device> list = deviceService.findListByStatus(14);
        if (list == null) return;
        PageInfo pageInfo = PageInfo.of(list);
        StringBuilder key = new StringBuilder();
        for (Device device : list) {
            if (device.getPay() != 0) continue; //TODO 临时增加控制是否开启bell
            // 只在设置的时间内可以警报，其他未知情况全部跳过
            if (isNeedBell(currTime, device.getAgentId(), device.getHospitalId(), device.getDepart(),device.getMac())) {
                key.append(device.getCode()).append(Constant.SIGN_FEN_HAO);
            }
        }
        if (key.length() > 0) {
            String[] array;
            Map<String, String> map = moduleLockService.getHardwareInfo(key.substring(0, key.length() - 1));
            for (String info : map.values()) {
                logger.debug(info);
                array = info.split(Constant.SIGN_FEN_HAO);
                if (array.length > 2 && "2".equals(array[1])) {
                    if(Constant.MODEL_DEV.equals(model) ){
                        logger.warn("开发模式不响铃，当前DID:{}", array[0]);
                    }else{
                        moduleLockService.deviceBeep(array[0]);
                    }

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
        Uptime midday = getUptime(3, agentId, hospitalId, depart); // 午休时间
        boolean isTrue = false, isTime = false;
        if (midday != null && !midday.isEmpty) {
            isTrue = currTime > midday.start && currTime < midday.stop;
        }
        Uptime uptime = getUptime(2, agentId, hospitalId, depart); // 运行时间
        if (uptime != null && !uptime.isEmpty) {
            isTrue |=  (currTime >= uptime.start || currTime <= uptime.stop);
            isTime = !isTrue && (currTime - uptime.stop) % INTERVAL_TIME == 0;
        }
        logger.debug("did: isTrue=>{} isTime=>{}", isTrue, isTime);
        return isTime || (isTrue && getUsingCount(did, System.currentTimeMillis()/1000) <= 0);
    }

    private Uptime getUptime(int type, int agentId, int hospitalId, int depart) {
        String key = type + "-" + agentId + "-" + hospitalId + "-" + depart;
        if (uptimeMap.containsKey(key)) return uptimeMap.get(key);
        JsonObject jsonObject = getJsonObject(type, new int[]{0, agentId, hospitalId, depart});
        Uptime uptime = new Uptime();
        if (jsonObject != null && jsonObject.has("data") && jsonObject.get("data").isJsonObject()) {
            JsonObject data = jsonObject.get("data").getAsJsonObject();
            uptime.start = data.get("startTime").getAsInt();
            uptime.stop = data.get("stopTime").getAsInt() + DELAY_TIME;
            uptimeMap.put(key, uptime);
        } else {
            uptime.isEmpty = true;
            uptimeMap.put(key, uptime);
        }
        return uptime;
    }

    private int getUsingCount(String did, long time) {
        return moduleWxService.getCountByUsingDid(did, time);
    }

    private JsonObject getJsonObject(int type, int[] kid) {
        String result;
        for (int i = 3; i > -1; i--) {
            //logger.debug("type:{} key:{} kid:{}", type, i, kid[i]);
            result = moduleWxService.queryUptime(type, i, kid[i]);
            if (result == null) continue;
            JsonObject object = new JsonParser().parse(result).getAsJsonObject();
            if (object.has("code") && object.get("code").getAsInt() == 200) return object;
        }
        return null;
    }

    private class Uptime {
        boolean isEmpty;
        int start;
        int stop;
    }
}
