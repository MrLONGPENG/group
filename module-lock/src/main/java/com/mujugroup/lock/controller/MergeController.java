package com.mujugroup.lock.controller;


import com.mujugroup.lock.service.MergeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * 获取硬件信息数据
     * @param param  锁ID-lockId 多个用分号分隔
     */
    @RequestMapping(value = "/getHardwareInfo", method = RequestMethod.POST)
    public Map<String, String> getHardwareInfo(String param) {
        return mergeService.getHardwareInfo(param);
    }
}

