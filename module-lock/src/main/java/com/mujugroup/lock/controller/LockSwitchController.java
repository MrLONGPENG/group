package com.mujugroup.lock.controller;


import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.lock.model.LockSwitch;
import com.mujugroup.lock.service.LockSwitchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/switch")
@Api(description = "开关锁记录接口")
public class LockSwitchController {

    private final LockSwitchService lockSwitchService;

    @Autowired
    public LockSwitchController(LockSwitchService lockSwitchService) {
        this.lockSwitchService = lockSwitchService;
    }

    @ApiOperation(value = "获取最近的十次开关锁记录", notes = "获取最近的十次开关锁记录")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(@ApiParam(value = "业务编号") @RequestParam(value = "did", required = false, defaultValue = "") String did
            , @ApiParam(value = "锁编号") @RequestParam(value = "bid", required = false, defaultValue = "") String bid
    ) throws BaseException {
        List<LockSwitch> lockSwitchList = lockSwitchService.getLockStatusList(did, bid);
        return ResultUtil.success(lockSwitchList);
    }
}
