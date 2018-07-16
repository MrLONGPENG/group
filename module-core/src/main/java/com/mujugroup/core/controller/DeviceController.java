package com.mujugroup.core.controller;


import com.lveqia.cloud.common.AESUtil;
import com.lveqia.cloud.common.ResultUtil;
import com.mujugroup.core.bean.DeviceBean;
import com.mujugroup.core.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/device")
public class DeviceController {
    private final Logger logger = LoggerFactory.getLogger(DeviceController.class);
    private DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public String query(String did) {
        logger.info("device--query");
        DeviceBean bean =  deviceService.findDeviceBeanByDid(did);
        if(bean!=null){
            return ResultUtil.success(bean);
        }
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

}
