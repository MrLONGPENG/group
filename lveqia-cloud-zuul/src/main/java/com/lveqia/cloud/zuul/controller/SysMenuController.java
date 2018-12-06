package com.lveqia.cloud.zuul.controller;


import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.zuul.objeck.vo.AddMenuVo;
import com.lveqia.cloud.zuul.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/sys/menu")
@Api(description = "系统菜单相关接口")
public class SysMenuController {

    private final SysMenuService sysMenuService;
    private final Logger logger = LoggerFactory.getLogger(SysMenuController.class);

    @Autowired
    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    /**
     * 当前用户拥有的菜单列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "菜单列表查询接口", notes = "查询当前用户拥有的菜单列表")
    public String list(@ApiParam(hidden = true) long uid) {
        logger.debug("/sys/menu/list uid:{}", uid);
        return ResultUtil.success(sysMenuService.getMenusByUserId(uid));
    }

    @ApiOperation(value = "添加菜单", notes = "添加菜单")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addMenu(@Validated @ModelAttribute AddMenuVo addMenuVo
            , @ApiParam(hidden = true) long uid) throws BaseException {
        return ResultUtil.success(sysMenuService.addMenu(uid, addMenuVo));
    }

}
