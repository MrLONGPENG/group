package com.mujugroup.core.service.feign.error;


import com.mujugroup.core.service.feign.ModuleLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ModuleLockServiceError implements ModuleLockService {

    private final Logger logger = LoggerFactory.getLogger(ModuleLockServiceError.class);

    @Override
    public String beep(String did) {
        logger.warn("Remote call module-lock-beep[{}] failure", did);
        return null;
    }

    @Override
    public Map<String, String> getHardwareInfo(String param) {
        logger.warn("Remote call module-lock-getHardwareInfo[{}] failure", param);
        return new HashMap<>();
    }

}
