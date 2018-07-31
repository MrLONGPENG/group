package com.mujugroup.wx.service.task;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mujugroup.wx.service.feign.ModuleCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.Date;


@Component
public class BellTask {
    private final Logger logger = LoggerFactory.getLogger(BellTask.class);

    private final ModuleCoreService moduleCoreService;

    @Autowired
    public BellTask(ModuleCoreService moduleCoreService) {
        this.moduleCoreService = moduleCoreService;
    }

    @Scheduled(cron="0 0/30 8-20 * * *")
    public void onCron(){
        logger.debug("date: {}", new Date());
        String result =  moduleCoreService.deviceList(1, 2, 14);
        if(result == null) return;
        JsonObject returnData = new JsonParser().parse(result).getAsJsonObject();
        if(returnData.get("code").getAsInt() == 200 && returnData.has("data")){
            JsonArray data = returnData.getAsJsonArray("data");
            for (JsonElement element : data) {
                if(element instanceof  JsonObject){
                    JsonObject object = (JsonObject)element;
                    if(object.get("run").getAsInt() ==1){


                    }
                }
            }
        }
    }
}
