package com.mujugroup.data.service.feign.error;

import com.mujugroup.data.service.feign.ModuleCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ModuleCoreServiceError implements ModuleCoreService {

    private final Logger logger = LoggerFactory.getLogger(ModuleCoreServiceError.class);


    @Override
    public Map<String, String> getActiveCount(String param) {
        logger.debug("data->remote core fail, method:getActiveCount param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getTotalActiveCount(String param) {
        logger.debug("data->remote core fail, method:getTotalActiveCount param:{}",param);
        return new HashMap<>();
    }
}
