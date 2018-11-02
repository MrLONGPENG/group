package com.lveqia.cloud.zuul.controller;



import com.lveqia.cloud.zuul.service.MergeService;
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
     * 根据DID获取床位信息
     * @param param 多个DID,“;”分割
     * @return 床位信息
     */
    @RequestMapping(value = "/getNameByUid",method = RequestMethod.POST)
    public Map<String, String> getNameByUid(@RequestParam(value = "param") String param){
        return mergeService.getNameByUid(param);
    }
}
