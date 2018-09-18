package com.lveqia.cloud.zuul.controller;


import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.zuul.service.SysMenuService;
import com.lveqia.cloud.zuul.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    private final SysMenuService sysMenuService;
    private final SysUserService sysUserService;

    @Autowired
    public SysMenuController(SysMenuService sysMenuService, SysUserService sysUserService) {
        this.sysMenuService = sysMenuService;
        this.sysUserService = sysUserService;
    }

    @RequestMapping(value = "/list")
    public String list(){
        UserInfo userInfo = sysUserService.getCurrInfo();
        if(userInfo == null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        return ResultUtil.success(sysMenuService.getMenusByUserId(userInfo.getId()));
    }

}
