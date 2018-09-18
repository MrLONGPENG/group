package com.lveqia.cloud.zuul.controller;

import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.zuul.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/sys/auth")
public class AuthController {

    private final SysUserService sysUserService;

    @Autowired
    public AuthController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @ResponseBody
    @RequestMapping(value = "/getUserId")
    public long getUserId(){
        UserInfo user = sysUserService.getCurrInfo();
        return user!=null ? user.getId() : -1;
    }


}
