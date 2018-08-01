package com.mujugroup.core.controller;

import com.lveqia.cloud.common.ResultUtil;
import com.mujugroup.core.bean.StatusAidBean;
import com.mujugroup.core.bean.StatusHidBean;
import com.mujugroup.core.bean.StatusOidBean;
import com.mujugroup.core.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/status")
@Api(description="设备状态信息相关接口")
public class StatusController {
    private final Logger logger = LoggerFactory.getLogger(StatusController.class);
    private DeviceService deviceService;

    @Autowired
    public StatusController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @ApiOperation(value="查询设备当前状态", notes="根据代理商或科室查询拥有设备状态")
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public String info(@ApiParam(value="代理商ID")@RequestParam(name="aid") int aid
            , @ApiParam(value="医院ID(0或不填为所有医院)")@RequestParam(name="hid", required=false
            , defaultValue="0") int hid, @ApiParam(value="科室ID(0或不填为所有科室)")@RequestParam(name="oid"
            , required=false, defaultValue="0") int oid) {
        logger.debug("status--query {} {} {}", aid, hid, oid);
        if(oid!=0) {
            List<StatusOidBean> list = deviceService.findGroupByOid(aid, hid, oid);
            String desc = "paymentInfo:{DID;订单号;支付金额(分);支付时间;到期时间(秒)};hardwareInfo:{BID;" +
                    "开锁状态;电池电量;最后上报时间;信号;温度;固件版本;硬件版本;充电电压;电池电压;充电电流}";
            if(list !=null) return ResultUtil.success(list, null, desc);
        }else if(hid!=0) {
            List<StatusHidBean> list = deviceService.findGroupByHid(aid, hid);
            if(list !=null) return ResultUtil.success(list);
        }else {
            List<StatusAidBean> list = deviceService.findGroupByAid(aid);
            if(list !=null) return ResultUtil.success(list);
        }
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

}
