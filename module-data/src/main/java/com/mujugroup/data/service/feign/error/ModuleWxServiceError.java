package com.mujugroup.data.service.feign.error;

import com.lveqia.cloud.common.objeck.to.AidHidOidTO;
import com.lveqia.cloud.common.objeck.to.OrderTO;
import com.lveqia.cloud.common.objeck.to.PageTO;
import com.mujugroup.data.service.feign.ModuleWxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ModuleWxServiceError implements ModuleWxService {

    private final Logger logger = LoggerFactory.getLogger(ModuleWxServiceError.class);

    @Override
    public Map<String, String> getUserCount(String param) {
        logger.warn("data->remote wx fail, method:getTotalUserCount param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getUsageCount(String param) {
        logger.warn("data->remote wx fail, method:getUsageCount param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getUsageRate(String param) {
        logger.warn("data->remote wx fail, method:getUsageRate param:{}",param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getTotalProfit(String param) {
        logger.warn("data->remote wx fail, method:getTotalProfit param:{}",param);
        return new HashMap<>();
    }

    @Override
    public PageTO<OrderTO> getOrderList(AidHidOidTO aidHidOidDto) {
        logger.warn("data->remote wx fail, method:getOrderList param:{}",aidHidOidDto);
        return null;
    }

}
