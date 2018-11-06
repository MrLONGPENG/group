package com.mujugroup.lock.controller;


import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.lock.model.LockRecord;
import com.mujugroup.lock.service.LockRecordService;
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
@RequestMapping("/lockRecord")
@Api(description = "开关锁记录接口")
public class LockRecordController {

    private final LockRecordService lockRecordService;

    @Autowired
    public LockRecordController(LockRecordService lockRecordService) {
        this.lockRecordService = lockRecordService;
    }

    @ApiOperation(value = "获取最近的十次开关锁记录", notes = "获取最近的十次开关锁记录")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(@ApiParam(value = "业务编号") @RequestParam(value = "did", required = false, defaultValue = "") String did
            , @ApiParam(value = "锁编号") @RequestParam(value = "bid", required = false, defaultValue = "") String bid
    ) throws BaseException {
        List<LockRecord> lockRecordList = lockRecordService.getLockStatusList(did,bid);
        if (lockRecordList != null && lockRecordList.size() > 0) {
            return ResultUtil.success(lockRecordList);
        } else {
            return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        }
    }
}
