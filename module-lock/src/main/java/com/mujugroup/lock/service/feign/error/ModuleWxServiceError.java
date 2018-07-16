package com.mujugroup.lock.service.feign.error;

import com.mujugroup.lock.service.feign.ModuleWxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ModuleWxServiceError implements ModuleWxService {

    private final Logger logger = LoggerFactory.getLogger(ModuleWxServiceError.class);
    @Override
    public String usingNotify(String bid, String lockStatus) {
        logger.warn("Remote call module-wx failure");
        return null;
    }
}
