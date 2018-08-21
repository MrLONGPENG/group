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

    /**
     * 获取指定粒度范围 激活数据
     * @param param 代理商ID,医院ID,科室ID,粒度类型(1:日 2:周 3:月),开始时间戳,结束时间戳
     */
    @RequestMapping(value = "/getActiveCount",method = RequestMethod.POST)
    public Map<String, String> getActiveCount(@RequestParam(value = "param") String param){
        return mergeService.getActiveCount(param);
    }

    /**
     * 获取到指定时间的激活数(多组数据用“;”分割)
     * @param param 代理商ID,医院ID,科室ID,结束时间戳
     */
    @RequestMapping(value = "/getTotalActiveCount",method = RequestMethod.POST)
    public Map<String, String> getTotalActiveCount(@RequestParam(value = "param") String param){
        return mergeService.getTotalActiveCount(param);
    }
}
