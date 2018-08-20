package com.mujugroup.core.controller;


import com.mujugroup.core.service.MergeService;
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

    @RequestMapping(value = "/getActiveValue",method = RequestMethod.POST)
    public Map<String, String> getActiveValue(@RequestParam(value = "param") String param){
        return mergeService.getActiveValue(param);
    }

    @RequestMapping(value = "/getTotalActiveCount",method = RequestMethod.POST)
    public Map<String, String> getTotalActiveCount(@RequestParam(value = "param") String param){
        return mergeService.getTotalActiveCount(param);
    }
}
