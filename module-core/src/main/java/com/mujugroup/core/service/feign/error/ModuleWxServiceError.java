package com.mujugroup.core.service.feign.error;

import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.model.Device;
import com.mujugroup.core.service.feign.ModuleWxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ModuleWxServiceError implements ModuleWxService {


    private final Logger logger = LoggerFactory.getLogger(ModuleWxServiceError.class);

    @Override
    public Map<String, String> getPayCount(String param) {
        logger.debug("Remote call module-wx-getPayCount[{}] failure", param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getPaymentInfo(String param) {
        logger.debug("Remote call module-wx-getPaymentInfo[{}] failure", param);
        return new HashMap<>();
    }

    @Override
    public String queryUptime(int type, int key, int kid) {
        return ResultUtil.error(ResultUtil.CODE_REMOTE_CALL_FAIL);
    }

    @Override
    public int getCountByUsingDid(String did, long time) {
        return 1;
    }
}
