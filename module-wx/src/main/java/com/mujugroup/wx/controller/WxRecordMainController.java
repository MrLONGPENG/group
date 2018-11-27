package com.mujugroup.wx.controller;


import com.mujugroup.wx.service.WxRecordMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/wxRecordMain")
public class WxRecordMainController {

    private WxRecordMainService wxRecordMainService;

    @Autowired
    public WxRecordMainController(WxRecordMainService wxRecordMainService) {
        this.wxRecordMainService = wxRecordMainService;
    }

}
