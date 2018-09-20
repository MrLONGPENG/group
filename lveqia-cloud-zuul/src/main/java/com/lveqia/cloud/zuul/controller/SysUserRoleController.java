package com.lveqia.cloud.zuul.controller;


import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.zuul.service.SysUserRoleService;
import com.lveqia.cloud.zuul.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/sys/userRole")
@Api(description="用户与角色关系相关接口")
public class SysUserRoleController {

    private final SysUserService sysUserService;
    private final SysUserRoleService sysUserRoleService;
    private final Logger logger = LoggerFactory.getLogger(SysUserRoleController.class);
    @Autowired
    public SysUserRoleController(SysUserService sysUserService, SysUserRoleService sysUserRoleService) {
        this.sysUserService = sysUserService;
        this.sysUserRoleService = sysUserRoleService;
    }


    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    @ApiOperation(value="修改用户的角色信息", notes="根据用户更新的角色信息")
    public String putRidToUid(@ApiParam(value="角色ID", required = true) @RequestParam(name="uid") int uid
            , @ApiParam(value="菜单ID组", required = true) @RequestParam(name="rid") int[] rid){
        logger.debug("put uid:{} rid:{}", uid, rid);
        UserInfo userInfo = sysUserService.getCurrInfo();
        if(userInfo == null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        if(sysUserRoleService.putRidToUid(uid, rid) == rid.length) return ResultUtil.success();
        return ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR);
    }

}
