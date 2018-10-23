package com.lveqia.cloud.zuul.service.feign.error;

import com.lveqia.cloud.zuul.service.feign.ModuleCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class ModuleCoreServiceError implements ModuleCoreService {
    private final Logger logger = LoggerFactory.getLogger(ModuleCoreServiceError.class);

    @Override
    public int addAuthData(int uid, String[] authData) {
        logger.debug("addAuthData");
        return 0;
    }

    @Override
    public Map<String, String> getAuthData(long uid) {
        return new HashMap<>();
    }
}
