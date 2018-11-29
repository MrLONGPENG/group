package com.mujugroup.wx.controller;


import com.mujugroup.wx.service.WxDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/wxDeposit")
public class WxDepositController {

    private WxDepositService wxDepositService;

    @Autowired
    public WxDepositController(WxDepositService wxDepositService) {
        this.wxDepositService = wxDepositService;
    }


}
