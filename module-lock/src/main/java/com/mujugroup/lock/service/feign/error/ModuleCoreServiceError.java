package com.mujugroup.lock.service.feign.error;
import com.mujugroup.lock.service.feign.ModuleCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
@Component
public class ModuleCoreServiceError implements ModuleCoreService {
    private Map<String, String> EMPTY_MAP = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(ModuleCoreServiceError.class);
    @Override
    public Map<String, String> getAuthData(String uid) {
        logger.warn("data->remote core fail, method:getAuthData param:{}",uid);
        return EMPTY_MAP;
    }

    @Override
    public Map<String, String> getDepartmentById(String param) {
        logger.warn("data->remote core fail, method:getDepartmentById param:{}",param);
        return EMPTY_MAP;
    }

    @Override
    public Map<String, String> getBedInfoByDid(String param) {
        logger.warn("data->remote core fail, method:getBedInfoByDid param:{}",param);
        return EMPTY_MAP;
    }
}
