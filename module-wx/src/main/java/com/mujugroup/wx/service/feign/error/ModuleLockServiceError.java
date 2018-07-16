package com.mujugroup.wx.service.feign.error;


import com.mujugroup.wx.service.feign.ModuleLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ModuleLockServiceError implements ModuleLockService {

    private final Logger logger = LoggerFactory.getLogger(ModuleLockServiceError.class);

    @Override
    public String bidToDid(String bid) {
        logger.warn("Remote call module-lock-bidToDid failure");
        return null;
    }

    @Override
    public String getStatus(String did) {
        logger.warn("Remote call module-lock-getStatus failure");
        return null;
    }

    @Override
    public String deviceUnlock(String did) {
        logger.warn("Remote call module-lock-deviceUnlock failure");
        return null;
    }
}
