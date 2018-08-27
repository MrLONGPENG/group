package com.mujugroup.wx.controller;

import com.lveqia.cloud.common.to.AidHidOidTO;
import com.lveqia.cloud.common.to.OrderTO;
import com.lveqia.cloud.common.to.PageTO;
import com.mujugroup.wx.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageTO<OrderTO> getOrderList(@RequestBody AidHidOidTO aidHidOidDto){
        return feignService.getOrderList(aidHidOidDto);
    }

}
