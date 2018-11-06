package com.lveqia.cloud.zuul.controller;


import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.objeck.vo.UserVo;
import com.lveqia.cloud.zuul.objeck.vo.user.UserAddVo;
import com.lveqia.cloud.zuul.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/sys/user")
@Api(description = "系统用户相关接口")
public class SysUserController {

    private final SysUserService sysUserService;
    private final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "用户信息查询接口", notes = "查询当前用户的基本信息")
    public String info() {
        UserInfo userInfo = sysUserService.getCurrInfo();
        if (userInfo == null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        return ResultUtil.success(userInfo);
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "系统用户查询接口", notes = "可根据用户名、账号模糊查询全部用户")
    public String list(@RequestParam(value = "fuzzy", required = false) boolean fuzzy
            , @RequestParam(value = "name", required = false) String name
            , @RequestParam(value = "username", required = false) String username
    ) {
        logger.debug("/sys/user/list name:{} username:{}", name, username);
        UserInfo userInfo = sysUserService.getCurrInfo();
        if (userInfo == null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        return ResultUtil.success(sysUserService.getSysUserList(fuzzy, name, username));
    }

    // TODO: 2018-09-27
    @RequestMapping(value = "/sub", method = RequestMethod.GET)
    @ApiOperation(value = "子用户查询接口")
    public String sub() {
        logger.debug("/sys/user/sub");
        UserInfo userInfo = sysUserService.getCurrInfo();
        if (userInfo == null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        List<UserVo> list = sysUserService.getUserTreeList((int) userInfo.getId());
        return ResultUtil.success(list);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "系统用户注册接口", notes = "根据用户名、手机号以及密码注册系统用户")
    public String add(@Validated @ModelAttribute UserAddVo userAddVo) throws BaseException {
        UserInfo userInfo = sysUserService.getCurrInfo();
        if (userInfo == null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        if (sysUserService.addUser(userInfo.getId(), userAddVo) == 1) {
            return ResultUtil.success("注册成功!");
        } else {
            return ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR, "注册失败!");
        }
    }

    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    @ApiOperation(value = "用户信息更新接口", notes = "可以按条件更新指定用户新")
    public String put(@RequestParam(value = "id", required = false) int id
            , @RequestParam(value = "enabled", required = false) boolean enabled) {
        UserInfo userInfo = sysUserService.getCurrInfo();
        if (userInfo == null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        if (id == userInfo.getId()) return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT, "无法自己禁用自己");
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setEnabled(enabled);
        if (sysUserService.putUser(sysUser) == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }


    @RequestMapping(value = "/get/{uid}", method = RequestMethod.GET)
    @ApiOperation(value = "获取单个用户接口", notes = "根据ID获取指定用户信息")
    public String get(@ApiParam(value = "用户ID", required = true) @PathVariable(name = "uid") int uid) {
        UserInfo userInfo = sysUserService.getCurrInfo();
        if (userInfo == null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        SysUser sysUser = sysUserService.getUser(uid);
        if (sysUser != null) {
            return ResultUtil.success(sysUser);
        } else {
            return ResultUtil.code(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }


    @RequestMapping(value = "/del/{uid}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户接口", notes = "删除用户,需要删除用户关联的角色")
    public String del(@ApiParam(value = "用户ID", required = true) @PathVariable(name = "uid") int uid) {
        UserInfo userInfo = sysUserService.getCurrInfo();
        if (userInfo == null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        if (uid == userInfo.getId()) return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT, "无法自己删除自己");
        if (sysUserService.delUser(uid) == 1) {
            return ResultUtil.success("删除成功");
        } else {
            return ResultUtil.code(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ApiOperation(value = "用户密码修改接口", notes = "在已登录的情况下，通过验证旧密码，更新新密码")
    public String modify(String oldPassword, String newPassword) {
        UserInfo userInfo = sysUserService.getCurrInfo();
        if (userInfo == null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        try {
            sysUserService.modify(userInfo, oldPassword, newPassword);
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
        return ResultUtil.success("修改密码成功!");

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "系统用户更新接口", notes = "可以更换用户昵称、住宅电话、地址以及密码相关信息")
    public String update(String name, String telephone, String address, String password) {
        UserInfo userInfo = sysUserService.getCurrInfo();
        if (userInfo == null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        return sysUserService.update(userInfo, name, telephone, address, password)
                ? ResultUtil.success("更新成功!")
                : ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR, "更新失败!");

    }
}
