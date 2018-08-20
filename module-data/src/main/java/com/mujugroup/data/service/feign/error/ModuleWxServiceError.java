package com.mujugroup.data.service.feign.error;

import com.mujugroup.data.service.feign.ModuleWxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class ModuleWxServiceError implements ModuleWxService {

    private final Logger logger = LoggerFactory.getLogger(ModuleWxServiceError.class);

    @Override
    public Map<String, String> getTotalUserCount(String param) {
        logger.debug("data->remote WX fail, method:getTotalUserCount param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getYesterdayUsageCount(String param) {
        logger.debug("data->remote WX fail, method:getYesterdayUsageCount param:{}",param);
        return new HashMap<>();
    }
}
