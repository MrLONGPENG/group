package com.mujugroup.wx.service.feign.error;


import com.mujugroup.wx.service.feign.ModuleCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ModuleCoreServiceError implements ModuleCoreService {

    private final Logger logger = LoggerFactory.getLogger(ModuleCoreServiceError.class);

    @Override
    public String deviceQuery(String did) {
        logger.warn("Remote call module-core failure");
        return null;
    }
}
