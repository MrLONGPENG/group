package com.mujugroup.data.controller;

import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.data.objeck.vo.DeviceVo;
import com.mujugroup.data.service.DeviceServcie;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
@Api(description = "数据模块设备接口")
public class DeviceController {
    private final DeviceServcie deviceServcie;

    @Autowired
    public DeviceController(DeviceServcie deviceServcie) {
        this.deviceServcie = deviceServcie;
    }

    @ApiOperation(value = "获取设备详情", notes = "根据条件获取设备详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String getDeviceDetailByDid(@ApiParam(value = "业务编号") @RequestParam(value = "did") String did) throws BaseException {
        DeviceVo deviceVo = deviceServcie.getDeviceDetailByDid(did);
        return ResultUtil.success(deviceVo);
    }
}
