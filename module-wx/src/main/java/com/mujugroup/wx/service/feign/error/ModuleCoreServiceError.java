package com.mujugroup.wx.service.feign.error;


import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.mujugroup.wx.service.feign.ModuleCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ModuleCoreServiceError implements ModuleCoreService {

    private final Logger logger = LoggerFactory.getLogger(ModuleCoreServiceError.class);

    @Override
    public InfoTo getDeviceInfo(String did, String bid) {
        logger.warn("Remote call module-core failure->getDeviceInfo");
        return null;
    }

    @Override
    public Map<String, String> getTotalActiveCount(String param) {
        logger.warn("Remote call module-core failure->param:{}", param);
        return new HashMap<>();
    }

    @Override
    public Map<Integer,String> findOidByHid(String hid) {
        logger.warn("Remote call module-core failure->param:{}", hid);
        return new HashMap<>();
    }

    @Override
    public String getHospitalName(Integer id) {
        return null;
    }
}
