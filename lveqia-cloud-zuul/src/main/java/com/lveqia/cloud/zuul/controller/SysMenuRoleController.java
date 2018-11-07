package com.lveqia.cloud.zuul.controller;

import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.zuul.service.SysMenuRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/sys/menuRole")
@Api(description="菜单与角色关系相关接口")
public class SysMenuRoleController {

    private final SysMenuRoleService sysMenuRoleService;
    private final Logger logger = LoggerFactory.getLogger(SysMenuRoleController.class);

    @Autowired
    public SysMenuRoleController(SysMenuRoleService sysMenuRoleService) {
        this.sysMenuRoleService = sysMenuRoleService;
    }


    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    @ApiOperation(value="修改角色的菜单信息", notes="根据角色更新的菜单信息")
    public String putMidToRid(@ApiParam(value="角色ID", required = true) @RequestParam(name="rid") int rid
            , @ApiParam(value="菜单ID组", required = true) @RequestParam(name="mid") int[] mid){
        logger.debug("put rid:{} mid:{}", rid, mid);
        if(sysMenuRoleService.putMidToRid(rid, mid) == mid.length) return ResultUtil.success();
        return ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR);
    }


    @RequestMapping(value = "/mid/{rid}", method = RequestMethod.GET)
    @ApiOperation(value="菜单ID查询接口(角色树)", notes="查询指定角色ID查询菜单ID，只返回ID{格式:[1,2..n]}")
    public String getMidByRid(@ApiParam(value="角色ID", required = true) @PathVariable(name="rid") int rid){
        return ResultUtil.success(sysMenuRoleService.getMidByRid(rid));
    }

}
