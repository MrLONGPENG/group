package com.mujugroup.core.controller;

import com.mujugroup.core.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 服务模块之间调用接口
 */
@RestController
@RequestMapping("/feign")
public class FeignController {


    private FeignService feignService;

    @Autowired
    public FeignController(FeignService feignService) {
        this.feignService = feignService;
    }

    @RequestMapping(value = "/getHospitalByAid",method = RequestMethod.POST)
    public Map<Integer, String> getHospitalByAid(@RequestParam(value = "aid") String aid){
        return feignService.getHospitalByAid(aid);
    }

}
