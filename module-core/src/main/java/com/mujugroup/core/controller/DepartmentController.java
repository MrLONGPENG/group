package com.mujugroup.core.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.objeck.vo.department.AddVo;
import com.mujugroup.core.objeck.vo.department.ListVo;
import com.mujugroup.core.objeck.vo.department.PutVo;
import com.mujugroup.core.objeck.vo.SelectVo;
import com.mujugroup.core.service.DepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/department")
@Api(description = "科室操作相关接口")
public class DepartmentController {
    private final DepartmentService departmentService;


    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @ApiOperation(value = "通过医院ID获取所有的科室", notes = "通过医院ID,或名称进行模糊匹配查询下拉列表")
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String select(@ApiParam(value = "医院ID") @RequestParam(value = "hid") int hid
            , @ApiParam(value = "科室名称") @RequestParam(value = "name", required = false
            , defaultValue = "") String name, @ApiParam(hidden = true) int uid) throws BaseException {
        List<SelectVo> list = departmentService.getSelectList(uid, hid, name);
        return ResultUtil.success(list);

    }

    @ApiOperation(value = "通过医院ID获取所有的科室", notes = "通过医院ID,或名称，可模糊匹配，获取所有的科室信息")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(@ApiParam(value = "当前页") @RequestParam(name = "pageNum", required = false
            , defaultValue = "1") int pageNum, @ApiParam(value = "每页显示") @RequestParam(name = "pageSize"
            , required = false, defaultValue = "10") int pageSize, @ApiParam(hidden = true) int uid
            , @ApiParam(value = "医院ID") @RequestParam(value = "hid", required = false, defaultValue = "") String hid
            , @ApiParam(value = "科室名称") @RequestParam(value = "name", required = false
            , defaultValue = "") String name
            , @ApiParam(value = "科室状态") @RequestParam(value = "status", required = false, defaultValue = "") String status
    ) throws BaseException {
        String ids = departmentService.checkUserData(uid, hid);
        PageHelper.startPage(pageNum, pageSize);
        List<ListVo> list = departmentService.findAll(ids, name, status);
        return ResultUtil.success(list, PageInfo.of(list));

    }

    @ApiOperation(value = "添加科室", notes = "添加科室")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addDepartment(@ApiParam(hidden = true) int uid
            , @Validated @ModelAttribute AddVo departmentVo) throws BaseException {
        if (departmentService.insert(uid, departmentVo)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

    @ApiOperation(value = "修改科室", notes = "修改科室")
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public String modifyDepartment(@Validated @ModelAttribute PutVo departmentPutVo
            , @ApiParam(hidden = true) int uid) throws BaseException {
        if (departmentService.update(uid, departmentPutVo)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

    @ApiOperation(value = "删除科室", notes = "删除科室")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String deleteDepartment(@ApiParam(hidden = true) int uid, @ApiParam(value = "科室ID"
            , required = true) @PathVariable(value = "id") String id ) throws BaseException {
        if (departmentService.delete(uid, id)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }

    }
}
