package com.mujugroup.data.service.feign.error;

import com.lveqia.cloud.common.objeck.to.PayInfoTo;
import com.lveqia.cloud.common.objeck.to.RequestTo;
import com.lveqia.cloud.common.objeck.to.OrderTo;
import com.lveqia.cloud.common.objeck.to.PageTo;
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
        logger.warn("data->remote wx fail, method:getTotalUserCount param:{}", param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getUsageCount(String param) {
        logger.warn("data->remote wx fail, method:getUsageCount param:{}", param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getUsageRate(String param) {
        logger.warn("data->remote wx fail, method:getUsageRate param:{}", param);
        return new HashMap<>();
    }

    @Override
    public Map<String, String> getTotalProfit(String param) {
        logger.warn("data->remote wx fail, method:getTotalProfit param:{}", param);
        return new HashMap<>();
    }

    @Override
    public PageTo<OrderTo> getOrderList(RequestTo aidHidOidDto) {
        logger.warn("data->remote wx fail, method:getOrderList param:{}", aidHidOidDto);
        return null;
    }

    @Override
    public PayInfoTo getPayInfoByDid(String did, int orderType) {
        logger.warn("data->remote wx fail, method:getPayInfoByDid param:{}", did);
        return null;
    }

    @Override
    public Map<String, String> getOrderEndTimeByDid(String param) {
        logger.warn("data->remote wx fail, method:getOrderEndTimeByDid param:{}", param);
        return new HashMap<>();
    }

}
