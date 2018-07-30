package com.lveqia.cloud.zuul.controller;


import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/my/user")
public class SysUserController {

    private SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @RequestMapping(value = "/info")
    public String info(){
        SysUser sysUser = sysUserService.getCurrInfo();
        if(sysUser == null) return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        return ResultUtil.success(sysUserService.getCurrInfo());
    }

}
