package com.mujugroup.core.service.feign.error;

import com.lveqia.cloud.common.ResultUtil;
import com.mujugroup.core.service.feign.ModuleWxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ModuleWxServiceError implements ModuleWxService {

    private final Logger logger = LoggerFactory.getLogger(ModuleWxServiceError.class);

    @Override
    public Map<String, String> getPayCount(String key) {
        logger.debug("Remote call module-wx-getPayCount[{}] failure", key);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getPaymentInfo(String key) {
        logger.debug("Remote call module-wx-getPaymentInfo[{}] failure", key);
        return new HashMap<>();
    }

    @Override
    public String queryUptime(int key, int kid) {
        return ResultUtil.error(ResultUtil.CODE_REMOTE_CALL_FAIL);
    }
}
