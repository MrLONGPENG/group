package com.mujugroup.lock.controller;

import com.mujugroup.lock.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
@Api(description="锁模块设备接口")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService lockService) {
        this.deviceService = lockService;
    }


    @ApiOperation(value="锁模块开锁接口", notes="根据业务DID或设备BID开锁")
    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    public String unlock(String did){
        return deviceService.remoteCall(0, did);
    }

    @ApiOperation(value="锁模块查询接口", notes="根据业务DID查询")
    @RequestMapping(value = "/query", method =  RequestMethod.POST)
    public String query(String did){
        return deviceService.remoteCall(1, did);
    }

    @ApiOperation(value="锁模块响铃接口", notes="根据业务DID")
    @RequestMapping(value = "/beep", method = RequestMethod.POST )
    public String beep(String did){
        return deviceService.remoteCall(2, did);
    }

    @ApiOperation(value="锁模块蓝牙接口", notes="根据业务DID")
    @RequestMapping(value = "/ble", method = RequestMethod.POST )
    public String ble(String did){
        return deviceService.remoteCall(3, did);
    }

}
