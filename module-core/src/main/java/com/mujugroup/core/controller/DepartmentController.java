package com.mujugroup.core.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.objeck.vo.department.DepartmentVo;
import com.mujugroup.core.objeck.vo.department.ListVo;
import com.mujugroup.core.objeck.vo.department.PutVo;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @ApiOperation(value = "通过选中的医院ID获取所有的科室", notes = "通过选中的医院ID获取所有的科室,可以通过名称进行模糊匹配")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(
            @ApiParam(value = "当前页") @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum
            , @ApiParam(value = "每页显示") @RequestParam(name = "pageSize"
            , required = false, defaultValue = "10") int pageSize
            , @ApiParam(hidden = true) int uid
            , @ApiParam(value = "医院ID") @RequestParam(value = "hid", required = false, defaultValue = "0") int hid
            , @ApiParam(value = "科室名称") @RequestParam(value = "name", required = false, defaultValue = "") String name
    ) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<ListVo> list = departmentService.findAll(uid, hid, name);
            if (list != null && list.size() > 0) {
                return ResultUtil.success(list, PageInfo.of(list));
            } else {
                return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
            }
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }

    }

    @ApiOperation(value = "添加科室", notes = "添加科室")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addDepartment(@ApiParam(hidden = true) int uid, @ModelAttribute DepartmentVo departmentVo) {
        try {
            if (departmentService.insert(uid, departmentVo)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "修改科室", notes = "修改科室")
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public String modifyDepartment(@ApiParam(hidden = true) int uid, @ModelAttribute PutVo departmentPutVo) {
        try {
            if (departmentService.update(uid, departmentPutVo)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "删除科室", notes = "删除科室")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String modifyDepartment(@ApiParam(hidden = true) int uid, @ApiParam(value = "科室ID") @PathVariable(value = "id") String id

    ) {
        try {
            if (departmentService.delete(uid, id)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }
}
