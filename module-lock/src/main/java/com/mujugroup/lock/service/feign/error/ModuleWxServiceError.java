package com.mujugroup.lock.service.feign.error;

import com.lveqia.cloud.common.objeck.to.PayInfoTo;
import com.lveqia.cloud.common.objeck.to.UptimeTo;
import com.mujugroup.lock.service.feign.ModuleWxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ModuleWxServiceError implements ModuleWxService {
    private Map<String, String> EMPTY_MAP = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(ModuleWxServiceError.class);

    @Override
    public Map<String, String> getOrderEndTimeByDid(String param) {
        logger.warn("Remote call module-wx failure");
        return EMPTY_MAP;
    }

    @Override
    public PayInfoTo getPayInfoByDid(String did,int orderType) {
        logger.warn("Remote call module-wx getPayInfoByDid,{},{}", did,orderType);
        return null;
    }

    @Override
    public UptimeTo getUptimeTo(String aid, String hid, String oid) {
        logger.warn("Remote call module-wx getUptimeTo,param{},param{},param{}", aid, hid, oid);
        return null;
    }

    @Override
    public String usingNotify(String bid, String lockStatus) {
        logger.warn("Remote call module-wx failure");
        return null;
    }
}
