package com.lveqia.cloud.zuul.controller;


import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/sys/user")
@Api(description="系统用户相关接口")
public class SysUserController {

    private final SysUserService sysUserService;

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value="用户信息查询接口", notes="查询当前用户的基本信息")
    public String info(){
        SysUser sysUser = sysUserService.getCurrInfo();
        if(sysUser == null) return ResultUtil.error(ResultUtil.CODE_NOT_AUTHORITY);
        return ResultUtil.success(sysUser);
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value="系统用户注册接口", notes="根据用户名、手机号以及密码注册系统用户")
    public String register(String username, String password, String phone) {
        try {
            return sysUserService.register(username, password, phone) ? ResultUtil.success("注册成功!")
                    : ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR,"注册失败!");
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ApiOperation(value="用户密码修改接口", notes="在已登录的情况下，通过验证旧密码，更新新密码")
    public String modify(String oldPassword,  String newPassword){
        SysUser sysUser = sysUserService.getCurrInfo();
        if(sysUser == null) return ResultUtil.error(ResultUtil.CODE_NOT_AUTHORITY);
        try {
            sysUserService.modify(sysUser, oldPassword, newPassword);
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
        return  ResultUtil.success("修改密码成功!");

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value="系统用户更新接口", notes="可以更换用户昵称、住宅电话、地址以及密码相关信息")
    public String update(String name,  String telephone, String address, String password) {
        SysUser sysUser = sysUserService.getCurrInfo();
        if(sysUser == null) return ResultUtil.error(ResultUtil.CODE_NOT_AUTHORITY);
        return sysUserService.update(sysUser, name, telephone, address, password)
                ? ResultUtil.success("更新成功!")
                : ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR,"更新失败!");

    }
}
