package com.lveqia.cloud.zuul.controller;


import com.lveqia.cloud.common.objeck.info.UserInfo;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.zuul.service.SysMenuService;
import com.lveqia.cloud.zuul.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/sys/menu")
@Api(description="系统菜单相关接口")
public class SysMenuController {

    private final SysMenuService sysMenuService;
    private final SysUserService sysUserService;
    private final Logger logger = LoggerFactory.getLogger(SysMenuController.class);
    @Autowired
    public SysMenuController(SysMenuService sysMenuService, SysUserService sysUserService) {
        this.sysMenuService = sysMenuService;
        this.sysUserService = sysUserService;
    }

    /**
     * 当前用户拥有的菜单列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value="菜单列表查询接口", notes="查询当前用户拥有的菜单列表")
    public String list(){
        UserInfo userInfo = sysUserService.getCurrInfo();
        if(userInfo == null) return ResultUtil.error(ResultUtil.CODE_TOKEN_INVALID);
        logger.debug("/sys/menu/list uid:{}", userInfo.getId());
        return ResultUtil.success(sysMenuService.getMenusByUserId(userInfo.getId()));
    }

}
