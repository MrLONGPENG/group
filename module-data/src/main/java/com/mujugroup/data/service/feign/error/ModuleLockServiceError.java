package com.mujugroup.data.service.feign.error;

import com.lveqia.cloud.common.objeck.to.LockTo;
import com.mujugroup.data.service.feign.ModuleLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModuleLockServiceError implements ModuleLockService {
    private final Logger logger = LoggerFactory.getLogger(ModuleCoreServiceError.class);

    @Override
    public List<String> getFailNameByDid(String did) {
        logger.warn("data->remote lock fail, method:getFailNameByDid param:{}", did);
        return null;
    }

    @Override
    public LockTo getLockInfo(String bid) {
        logger.warn("data->remote lock fail, method:getLockInfo param:{}", bid);
        return null;
    }
}
