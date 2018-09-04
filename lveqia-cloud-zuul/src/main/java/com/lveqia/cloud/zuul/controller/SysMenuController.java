package com.lveqia.cloud.zuul.controller;


import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.zuul.model.SysUser;
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
        SysUser sysUser = sysUserService.getCurrInfo();
        if(sysUser == null) return ResultUtil.error(ResultUtil.CODE_NOT_AUTHORITY);
        return ResultUtil.success(sysMenuService.getMenusByUserId(sysUser.getId()));
    }

}
