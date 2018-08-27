package com.mujugroup.data.service.feign.error;

import com.lveqia.cloud.common.to.AidHidOidTO;
import com.lveqia.cloud.common.to.OrderTO;
import com.lveqia.cloud.common.to.PageTO;
import com.mujugroup.data.service.feign.ModuleWxService;
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
    public Map<String, String> getUserCount(String param) {
        logger.debug("data->remote wx fail, method:getTotalUserCount param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getUsageCount(String param) {
        logger.debug("data->remote wx fail, method:getUsageCount param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getUsageRate(String param) {
        logger.debug("data->remote wx fail, method:getUsageRate param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getOrderTypeById(String param) {
        logger.debug("data->remote wx fail, method:getOrderTypeById param:{}",param);
        return new HashMap<>();
    }

    @Override
    public PageTO<OrderTO> getOrderList(AidHidOidTO aidHidOidDto) {
        logger.debug("data->remote wx fail, method:getOrderList param:{}",aidHidOidDto);
        return null;
    }

}
