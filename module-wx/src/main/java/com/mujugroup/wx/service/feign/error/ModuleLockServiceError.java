package com.mujugroup.wx.service.feign.error;


import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.objeck.to.DataTo;
import com.mujugroup.wx.service.feign.ModuleLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class ModuleLockServiceError implements ModuleLockService {

    private final Logger logger = LoggerFactory.getLogger(ModuleLockServiceError.class);

    @Override
    public String unlock(String did) {
        logger.warn("Remote call module-lock-deviceUnlock failure");
        return null;
    }

    @Override
    public String getLockStatus(String did) {
        logger.warn("Remote call module-lock-getLockStatus failure");
        return null;
    }

    @Override
    public List<DataTo> getFailTimeoutRecordList(int pageNum, int pageSize) {
        logger.warn("Remote call module-lock-getFailTimeoutRecordList failure,{},{}", pageNum, pageSize);
        return null;
    }

    @Override
    public DataTo getRecordByDidAndLastRefresh(long did, long lastRefresh) {
        logger.warn("Remote call module-lock-getRecordByDidAndLastRefresh failure,{},{}", did, lastRefresh);
        return null;
    }
}
