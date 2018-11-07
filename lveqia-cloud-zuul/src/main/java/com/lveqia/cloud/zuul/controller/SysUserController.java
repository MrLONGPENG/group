package com.lveqia.cloud.zuul.controller;


import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.zuul.model.SysUser;
import com.lveqia.cloud.zuul.objeck.vo.user.AddVo;
import com.lveqia.cloud.zuul.objeck.vo.user.ListVo;
import com.lveqia.cloud.zuul.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



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
    public String list(@ModelAttribute ListVo listVo) {
        logger.debug("/sys/user/list name:{} username:{}", listVo.getName(), listVo.getUsername());
        return ResultUtil.success(sysUserService.getSysUserList(listVo));
    }

    // TODO: 2018-09-27
    @RequestMapping(value = "/sub", method = RequestMethod.GET)
    @ApiOperation(value = "子用户查询接口")
    public String sub(@ApiParam(value = "当前页") @RequestParam(name = "pageNum", required = false
            , defaultValue = "1") int pageNum, @ApiParam(value = "每页显示") @RequestParam(name = "pageSize"
            , required = false, defaultValue = "10") int pageSize, @ApiParam(hidden = true) int uid) {
        logger.debug("/sys/user/sub");
        return ResultUtil.success(sysUserService.getUserTreeList(uid, pageNum, pageSize));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "系统用户注册接口", notes = "根据用户名、手机号以及密码注册系统用户")
    public String add(@Validated @ModelAttribute AddVo userAddVo
            , @ApiParam(hidden = true) int uid) throws BaseException {
        if (sysUserService.addUser(uid, userAddVo) == 1) {
            return ResultUtil.success("注册成功!");
        } else {
            return ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR, "注册失败!");
        }
    }

    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    @ApiOperation(value = "用户信息更新接口", notes = "可以按条件更新指定用户新")
    public String put(@ApiParam(hidden = true) int uid, @RequestParam(value = "id", required = false) int id
            , @RequestParam(value = "enabled", required = false) boolean enabled) {
        if (id == uid) return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT, "无法自己禁用自己");
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setEnabled(enabled);
        if (sysUserService.putUser(sysUser) == 1) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }


    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取单个用户接口", notes = "根据ID获取指定用户信息")
    public String get(@ApiParam(value = "用户ID", required = true) @PathVariable(name = "id") int id) {
        SysUser sysUser = sysUserService.getUser(id);
        if (sysUser != null) {
            return ResultUtil.success(sysUser);
        } else {
            return ResultUtil.code(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }


    @RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除用户接口", notes = "删除用户,需要删除用户关联的角色")
    public String del(@ApiParam(value = "用户ID", required = true) @PathVariable(name = "id") int id
            , @ApiParam(hidden = true) int uid) {
        if (id == uid) return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT, "无法自己删除自己");
        if (sysUserService.delUser(id) == 1) {
            return ResultUtil.success("删除成功");
        } else {
            return ResultUtil.code(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ApiOperation(value = "用户密码修改接口", notes = "在已登录的情况下，通过验证旧密码，更新新密码")
    public String modify(@ApiParam(hidden = true) int uid, String oldPassword, String newPassword) {
        try {
            sysUserService.modify(uid, oldPassword, newPassword);
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
        return ResultUtil.success("修改密码成功!");

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "系统用户更新接口", notes = "可以更换用户昵称、住宅电话、地址以及密码相关信息")
    public String update(@ApiParam(hidden = true) int uid, String name, String telephone
            , String address, String password) {
        return sysUserService.update(uid, name, telephone, address, password)
                ? ResultUtil.success("更新成功!")
                : ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR, "更新失败!");

    }
}
