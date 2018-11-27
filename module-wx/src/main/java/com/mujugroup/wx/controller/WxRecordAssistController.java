package com.mujugroup.wx.controller;


import com.mujugroup.wx.service.WxRecordAssistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/wxRecordAssist")
public class WxRecordAssistController {

    private WxRecordAssistService wxRecordAssistService;

    @Autowired
    public WxRecordAssistController(WxRecordAssistService wxRecordAssistService) {
        this.wxRecordAssistService = wxRecordAssistService;
    }

}
