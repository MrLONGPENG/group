package com.mujugroup.wx.controller;


import com.mujugroup.wx.service.MergeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 数据聚合模块之间调用接口
 */
@RestController
@RequestMapping("/merge")
public class MergeController {

    private MergeService mergeService;

    @Autowired
    public MergeController(MergeService mergeService) {
        this.mergeService = mergeService;
    }

    @RequestMapping(value = "/getPayCount", method = RequestMethod.POST)
    Map<String, String> getPayCount(@RequestParam(value = "param") String param){
        return mergeService.getPayCount(param);
    }

    @RequestMapping(value = "/getPaymentInfo", method = RequestMethod.POST)
    Map<String, String> getPaymentInfo(@RequestParam(value = "param") String param){
        return mergeService.getPaymentInfo(param);
    }


    @RequestMapping(value = "/getTotalUserCount",method = RequestMethod.POST)
    public Map<String, String> getTotalUserCount(@RequestParam(value = "param") String param){
        return mergeService.getTotalUserCount(param);
    }

    @RequestMapping(value = "/getYesterdayUsageCount",method = RequestMethod.POST)
    public Map<String, String> getYesterdayUsageCount(@RequestParam(value = "param") String param){
        return mergeService.getYesterdayUsageCount(param);
    }
}
