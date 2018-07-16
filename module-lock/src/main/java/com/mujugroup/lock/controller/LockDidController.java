package com.mujugroup.lock.controller;

import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.StringUtil;
import com.mujugroup.lock.model.LockDid;
import com.mujugroup.lock.model.LockInfo;
import com.mujugroup.lock.service.LockDidService;
import com.mujugroup.lock.service.LockInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/did")
public class LockDidController {

    private final LockDidService lockDidService;
    private final LockInfoService lockInfoService;

    private final Logger logger = LoggerFactory.getLogger(LockDidController.class);
    @Autowired
    public LockDidController(LockDidService lockDidService, LockInfoService lockInfoService) {
        this.lockDidService = lockDidService;
        this.lockInfoService = lockInfoService;
    }

    @RequestMapping(value = "/getDeviceId")
    public String getDeviceId(String did){
        logger.debug("getDeviceId:" + did);
        return query(did, null);
    }

    @RequestMapping(value = "/query")
    public String query(String did, String bid){
        logger.debug("did:"+did +"  bid:"+bid);
        if(did == null && bid ==null) return ResultUtil.error(ResultUtil.CODE_PARAMETER_MISS);
        LockDid lockDid;
        if(did!=null){
            if(did.length()>9) return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT);
            lockDid = lockDidService.getLockDidByDid(StringUtil.autoFillDid(did));
        }else {
            lockDid = lockDidService.getLockDidByBid(bid);
        }
        if(lockDid == null) return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        return ResultUtil.success(lockDid);
    }

    @RequestMapping(value = "/status")
    public String getStatus(String did){
        logger.debug("getStatus");
        if(!StringUtil.isNumeric(did)) return null;
        LockDid lockDid = lockDidService.getLockDidByDid(StringUtil.autoFillDid(did));
        if(lockDid == null) return null;
        LockInfo lockInfo = lockInfoService.getLockInfoByDid(String.valueOf(lockDid.getLockId()));
        if(lockInfo == null) return null;
        return String.valueOf(lockInfo.getLockStatus());
    }


    @RequestMapping(value = "/bid")
    public String bidToBid(String bid){
        logger.debug("didToBid");
        if(bid == null) return null;
        LockDid lockDid = lockDidService.getLockDidByBid(bid);
        if(lockDid == null) return null;
        return StringUtil.autoFillDid(lockDid.getDid());
    }



}
