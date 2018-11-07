package com.lveqia.cloud.zuul.controller;


import com.lveqia.cloud.common.exception.ExistException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.zuul.model.SysRole;
import com.lveqia.cloud.zuul.service.SysRoleService;
import com.lveqia.cloud.zuul.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/sys/role")
@Api(description="系统角色相关接口")
public class SysRoleController {
    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;

    @Autowired
    public SysRoleController(SysUserService sysUserService, SysRoleService sysRoleService) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value="添加角色接口", notes="添加角色，同时设置自己拥有该角色")
    public String add(@ApiParam(value="角色英文名称", required = true) @RequestParam(name="name") String name
            , @ApiParam(value="角色中文名称", required = true) @RequestParam(name="desc")String desc
            , @ApiParam(hidden = true) int uid){
        try {
            if(sysRoleService.addRole(uid, name, desc)){
                return ResultUtil.success("添加成功");
            }else{
                return ResultUtil.code(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (ExistException|ParamException  e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }

    }

    @RequestMapping(value = "/del/{rid}", method = RequestMethod.DELETE)
    @ApiOperation(value="删除角色接口", notes="删除角色，若角色无其他人使用则彻底删除，否则只删自己引用")
    public String del(@ApiParam(value="角色ID", required = true) @PathVariable(name="rid") int rid
            , @ApiParam(hidden = true) int uid){
        if(sysRoleService.delRole(uid, rid)){
            return ResultUtil.success("删除成功");
        }else{
            return ResultUtil.code(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value="角色列表查询接口", notes="查询当前用户拥有的角色列表")
    public String list(@ApiParam(hidden = true) int uid){
        return ResultUtil.success(sysRoleService.getRoleListByUid(uid));
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ApiOperation(value="角色列表(包括菜单信息)查询接口", notes="查询当前用户角色列表,以及每个角色拥有的菜单")
    public String menu(@ApiParam(hidden = true) int uid){
        return ResultUtil.success(sysRoleService.getRoleMenuByUid(uid));
    }
    @RequestMapping(value="/list/{id}",method = RequestMethod.GET)
    @ApiOperation(value="通过用户的父ID获取对应的角色的查询接口",notes = "查询当前用户角色")
    public  String getUserRoleByUid(@ApiParam(value = "用户ID",required = true) @PathVariable(name="id") int id){
        List<SysRole> userRoleList=sysRoleService.getUserRoleByUid(id);
        if (userRoleList==null||userRoleList.size()<=0){
            return  ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        }
       return  ResultUtil.success(userRoleList);
    }

}
