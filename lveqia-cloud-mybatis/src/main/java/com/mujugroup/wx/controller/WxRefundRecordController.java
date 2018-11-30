package com.mujugroup.wx.controller;


import com.mujugroup.wx.service.WxRefundRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/wxRefundRecord")
public class WxRefundRecordController {

    private WxRefundRecordService wxRefundRecordService;

    @Autowired
    public WxRefundRecordController(WxRefundRecordService wxRefundRecordService) {
        this.wxRefundRecordService = wxRefundRecordService;
    }

}
