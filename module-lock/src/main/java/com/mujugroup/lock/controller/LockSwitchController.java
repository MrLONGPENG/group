package com.mujugroup.lock.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.lock.model.LockSwitch;
import com.mujugroup.lock.objeck.vo.unlock.ListVo;
import com.mujugroup.lock.service.LockSwitchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "获取开关锁记录", notes = "默认获取最近的十次开关锁记录")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(@ModelAttribute ListVo listVo) throws BaseException {
        PageHelper.startPage(listVo.getPageNum(), listVo.getPageSize());
        List<LockSwitch> lockSwitchList = lockSwitchService.getLockStatusList(listVo.getDid(), listVo.getBid(),listVo.getStartTime(),listVo.getEndTime());
        return ResultUtil.success(lockSwitchService.convert(lockSwitchList), PageInfo.of(lockSwitchList));
    }
}
