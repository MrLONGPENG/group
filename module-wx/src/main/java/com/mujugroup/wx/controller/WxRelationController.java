package com.mujugroup.wx.controller;


import com.mujugroup.wx.service.WxRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/relation")
public class WxRelationController {

    private WxRelationService wxRelationService;

    @Autowired
    public WxRelationController(WxRelationService wxRelationService) {
        this.wxRelationService = wxRelationService;
    }

    @RequestMapping(value = "/test")
    public String test(){
        return wxRelationService.test();
    }

}
