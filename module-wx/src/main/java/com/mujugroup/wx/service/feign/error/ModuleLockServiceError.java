package com.mujugroup.wx.service.feign.error;


import com.mujugroup.wx.service.feign.ModuleLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ModuleLockServiceError implements ModuleLockService {

    private final Logger logger = LoggerFactory.getLogger(ModuleLockServiceError.class);

    @Override
    public String unlock(String did) {
        logger.warn("Remote call module-lock-deviceUnlock failure");
        return null;
    }

    @Override
    public String getLockStatus(String did) {
        logger.warn("Remote call module-lock-getLockStatus failure");
        return null;
    }

}
