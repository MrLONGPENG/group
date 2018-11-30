package com.mujugroup.core.service.feign.error;

import com.lveqia.cloud.common.objeck.to.UptimeTo;
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
    public UptimeTo getUptimeTo(int aid, int hid, int oid) {
        logger.debug("Remote call module-wx-getUptimeTo aid:{} hid:{} oid:{}", aid, hid, oid);
        return null;
    }

    @Override
    public Map<String, String> getOrderEndTimeByDid(String param) {
        logger.debug("Remote call module-wx-getOrderEndTimeByDid[{}] failure", param);
        return new HashMap<>();
    }

    @Override
    public int getCountByUsingDid(String did, long time) {
        return 1;
    }
}
