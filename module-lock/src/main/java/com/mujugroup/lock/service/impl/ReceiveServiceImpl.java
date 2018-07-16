package com.mujugroup.lock.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mujugroup.lock.model.LockInfo;
import com.mujugroup.lock.service.LockInfoService;
import com.mujugroup.lock.service.ReceiveService;
import com.mujugroup.lock.service.feign.ModuleWxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;


@Service("receiveService")
public class ReceiveServiceImpl implements ReceiveService {
    private final Logger logger = LoggerFactory.getLogger(ReceiveServiceImpl.class);
    private final RestTemplate restTemplate;
    private final LockInfoService lockInfoService;
    private final ModuleWxService moduleWxService;

    @Autowired
    public ReceiveServiceImpl(RestTemplate restTemplate, LockInfoService lockInfoService
            , ModuleWxService moduleWxService) {
        this.restTemplate = restTemplate;
        this.lockInfoService = lockInfoService;
        this.moduleWxService = moduleWxService;
    }


    @Override
    public String receive(String info) {
        try {
            logger.info(info);
            JsonObject json = new JsonParser().parse(info).getAsJsonObject();
            switch (json.get("msgType").getAsInt()){
                case 200: switchLock(json.getAsJsonObject("result"), true);break;//开关锁
                case 201:   break;//定位信息上报
                case 400:   break;//故障信息上报
                case 401:   break;//其他信息上报
                case 1000:  switchLock(json.getAsJsonObject("result"), false);break;//普通上报消息（普通锁）
                case 2000:  break;//普通上报消息（助力锁）
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    private void switchLock(JsonObject result, boolean isNotify) {
        LockInfo lockInfo = lockInfoService.getLockInfoByDid(result.get("id").getAsString());
        if(lockInfo == null){
            lockInfoService.insert(formatLockInfo(result, lockInfo = new LockInfo()));
        }else{
            lockInfoService.update(formatLockInfo(result, lockInfo));
        }
        if(isNotify){
            moduleWxService.usingNotify(String.valueOf(lockInfo.getDid()),String.valueOf(lockInfo.getLockStatus()));
        }
    }

    private LockInfo formatLockInfo(JsonObject info, LockInfo lockInfo) {
        lockInfo.setBrand(1); // 设置品牌连旅
        if(info.has("id")){
            lockInfo.setDid(info.get("id").getAsLong());
        }
        if(info.has("mac")){
            lockInfo.setMac(info.get("mac").getAsString());
        }
        if(info.has("key")){
            lockInfo.setKey(info.get("key").getAsString());
        }
        if(info.has("firmwareVersion")){
            lockInfo.setFVersion(info.get("firmwareVersion").getAsInt());
        }
        if(info.has("hdwareVer")){
            lockInfo.setHVersion(info.get("hdwareVer").getAsInt());
        }
        if(info.has("longitude")){
            lockInfo.setLongitude(info.get("longitude").getAsBigDecimal());
        }
        if(info.has("latitude")){
            lockInfo.setLatitude(info.get("latitude").getAsBigDecimal());
        }
        if(info.has("csq")){
            lockInfo.setCsq(info.get("csq").getAsInt());
        }
        if(info.has("temp")){
            lockInfo.setTemp(info.get("temp").getAsInt());
        }
        if(info.has("vbus")){
            lockInfo.setCharge(info.get("vbus").getAsInt());
        }
        if(info.has("vbattery")){
            lockInfo.setVoltage(info.get("vbattery").getAsInt());
        }
        if(info.has("iCharge")){
            lockInfo.setElectric(info.get("iCharge").getAsInt());
        }
        if(info.has("upgradeFlag")){
            lockInfo.setUpgrade(info.get("upgradeFlag").getAsInt());
        }
        if(info.has("batteryStat")){
            lockInfo.setBatteryStat(info.get("batteryStat").getAsInt());
        }
        if(info.has("lockStatus")){
            lockInfo.setLockStatus(info.get("lockStatus").getAsInt());
        }
        if(info.has("lastRefresh")){
            lockInfo.setLastRefresh(new Date(info.get("lastRefresh").getAsLong()));
        }
        return lockInfo;
    }
}
