package com.mujugroup.data.service.feign.error;

import com.mujugroup.data.service.feign.ModuleAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ModuleAuthServiceError implements ModuleAuthService {

    private final Logger logger = LoggerFactory.getLogger(ModuleAuthServiceError.class);

    @Override
    public int getUserId(String sessionId) {
        logger.debug("data->remote jwt fail, method:getUserId {}", sessionId);
        return -2;
    }

}
