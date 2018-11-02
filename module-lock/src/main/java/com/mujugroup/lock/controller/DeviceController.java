package com.mujugroup.lock.controller;

import com.google.gson.JsonObject;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.mujugroup.lock.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
@Api(description="锁模块设备接口")
public class DeviceController {

    @Value("${spring.profiles.active}")
    private String model;
    private final DeviceService deviceService;
    private final Logger logger = LoggerFactory.getLogger(DeviceController.class);
    @Autowired
    public DeviceController(DeviceService lockService) {
        this.deviceService = lockService;
    }


    @ApiOperation(value="锁模块开锁接口", notes="根据业务DID或设备BID开锁")
    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    public String unlock(String did){
        return remoteCall(0, did);
    }

    @ApiOperation(value="锁模块查询接口", notes="根据业务DID查询")
    @RequestMapping(value = "/query", method =  RequestMethod.POST)
    public String query(String did){
        return remoteCall(1, did);
    }

    @ApiOperation(value="锁模块响铃接口", notes="根据业务DID")
    @RequestMapping(value = "/beep", method = RequestMethod.POST )
    public String beep(String did){
        return remoteCall(2, did);
    }

    @ApiOperation(value="锁模块蓝牙接口", notes="根据业务DID")
    @RequestMapping(value = "/ble", method = RequestMethod.POST )
    public String ble(String did){
        return remoteCall(3, did);
    }

    /**
     * 远程调用连旅接口
     * @param type 0：开锁 1：查询 2：寻车铃 3：蓝牙信息
     * @param did  业务ID(DID) 或 锁设备ID(BID)
     */
    private String remoteCall(int type, String did) {
        logger.debug("remoteCall:"+did);
        if(did == null) return ResultUtil.error(ResultUtil.CODE_PARAMETER_MISS);
        if(!StringUtil.isNumeric(did)) return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT);
        if(did.length()>9 && did.length()!=19) return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT);
        if(Constant.MODEL_DEV.equals(model)) {
            logger.info("远程调用锁接口，Did: {}, type: {}", did, type);
            return ResultUtil.success("{\"code\":200}");
        }
        JsonObject object = null;
        switch (type){
            case 0: object = deviceService.unlock(did);break;
            case 1: object = deviceService.query(did);break;
            case 2: object = deviceService.beep(did);break;
            case 3: object = deviceService.ble(did);break;
        }
        if(object==null ) return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        if(object.has("code")) return ResultUtil.code(object.get("code").getAsInt());
        return ResultUtil.success(object);
    }
}
