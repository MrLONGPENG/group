package com.mujugroup.core.service.task;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lveqia.cloud.common.DateUtil;
import com.mujugroup.core.model.Device;
import com.mujugroup.core.service.DeviceService;
import com.mujugroup.core.service.feign.ModuleLockService;
import com.mujugroup.core.service.feign.ModuleWxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class BellTask {

    private final DeviceService deviceService;
    private final ModuleWxService moduleWxService;
    private final ModuleLockService moduleLockService;
    private final Map<String,Uptime> uptimeMap = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(BellTask.class);
    @Autowired
    public BellTask(DeviceService deviceService, ModuleWxService moduleWxService, ModuleLockService moduleLockService) {
        this.deviceService = deviceService;
        this.moduleWxService = moduleWxService;
        this.moduleLockService = moduleLockService;
    }

    @Scheduled(cron="0 0/15 8-17 * * *")
    public void onCron(){
        logger.debug("BellTask date: {}", new Date());
        uptimeMap.clear();
        onDevice(1, 5);
    }

    private void onDevice(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Device> list=  deviceService.findListByStatus(14);
        if(list == null) return;
        PageInfo pageInfo =  PageInfo.of(list);
        StringBuilder key = new StringBuilder();
        for (Device device : list) {
            if(device.getPay()!=0) continue; //TODO 临时增加控制是否开启bell
            // 只在设置的时间内可以警报，其他未知情况全部跳过
            if(isNeedBell(device.getAgentId(), device.getHospitalId(), device.getDepart())){
                key.append(device.getCode()).append(",");
            }
        }
        if(key.length()>0){
            String[] array;
            Map<String, String> map =  moduleLockService.getHardwareInfo(key.substring(0, key.length()-1));
            for (String info : map.values()) {
                logger.debug(info);
                array = info.split(";");
                if(array.length> 2 && "2".equals(array[1])){
                    moduleLockService.deviceBeep(array[0]);
                }
            }
        }

        if(pageInfo.getPages() > pageNum){
            onDevice(pageNum+1, pageSize);
        }
    }

    /**
     * 只有在非开锁情况下需要警报，未知情况下不报
     */
    private boolean isNeedBell(Integer agentId, Integer hospitalId, Integer depart) {
        Uptime uptime = getUptime(agentId, hospitalId, depart);
        if(uptime!=null){
            int time = DateUtil.getTimesNoDate();
            return time > uptime.stop && time < uptime.start;
        }
        return false;
    }

    private Uptime getUptime(Integer agentId, Integer hospitalId, Integer depart) {
        String key = agentId+"-"+ hospitalId+"-"+ depart;
        if(uptimeMap.containsKey(key)) return uptimeMap.get(key);
        Uptime uptime = null;
        JsonObject jsonObject =  getJsonObject(new int[]{0, agentId, hospitalId, depart});
        if(jsonObject!=null && jsonObject.has("data") && jsonObject.get("data").isJsonObject()){
            JsonObject data = jsonObject.get("data").getAsJsonObject();
            uptime = new Uptime();
            uptime.start = data.get("startTime").getAsInt();
            uptime.stop  = data.get("stopTime").getAsInt();
            uptimeMap.put(key, uptime);
        }
        return uptime;
    }

    private JsonObject getJsonObject(int[] kid) {
        String result;
        for (int i = 3; i > -1; i--){
            logger.debug("key:{} kid:{}", i, kid[i]);
            result = moduleWxService.queryUptime(i, kid[i]);
            if(result == null) continue;
            JsonObject object = new JsonParser().parse(moduleWxService.queryUptime(i, kid[i])).getAsJsonObject();
            if(object.has("code") && object.get("code").getAsInt() == 200) return object;
        }
        return null;
    }

    private class Uptime {
        int start;
        int stop;
    }
}
