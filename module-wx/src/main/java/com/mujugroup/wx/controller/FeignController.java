package com.mujugroup.wx.controller;

import com.lveqia.cloud.common.dto.AidHidOidDto;
import com.lveqia.cloud.common.dto.OrderDto;
import com.mujugroup.wx.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务模块之间调用接口
 */
@RestController
@RequestMapping("/feign")
public class FeignController {

    private final FeignService feignService;

    @Autowired
    public FeignController(FeignService feignService) {
        this.feignService = feignService;
    }

    @ResponseBody
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public List<OrderDto> getOrderList(@RequestBody AidHidOidDto aidHidOidDto){
        return feignService.getOrderList(aidHidOidDto);
    }

}
