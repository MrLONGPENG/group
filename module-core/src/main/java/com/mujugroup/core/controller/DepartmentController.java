package com.mujugroup.core.controller;

import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.objeck.vo.Department.DepartmentVo;
import com.mujugroup.core.objeck.vo.Department.PutVo;
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
    public String list(@ApiParam(value = "医院ID") @RequestParam(value = "hid") String hid
            , @ApiParam(value = "科室名称") @RequestParam(value = "name", required = false, defaultValue = "") String name
    ) {
        List<SelectVO> departmentSelectVoList = departmentService.getListByHidOrName(hid, name);
        if (departmentSelectVoList != null && departmentSelectVoList.size() > 0) {
            return ResultUtil.success(departmentSelectVoList);
        } else {
            return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        }
    }

    @ApiOperation(value = "获取所有的爱汇科室", notes = "可以通过名称进行模糊匹配")
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String getDepartmentList(@ApiParam(value = "科室名称") @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        List<SelectVO> departmentSelectVoList = departmentService.getDepartmentList(name);
        if (departmentSelectVoList != null && departmentSelectVoList.size() > 0) {
            return ResultUtil.success(departmentSelectVoList);
        } else {
            return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        }
    }

    @ApiOperation(value = "添加科室", notes = "添加科室")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addDepartment(@ModelAttribute DepartmentVo departmentVo) {
        try {
            if (departmentService.insert(departmentVo)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (ParamException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "修改科室", notes = "修改科室")
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.PUT)
    public String modifyDepartment(@ApiParam(value = "科室ID") @PathVariable(value = "id") String id
            , @ModelAttribute PutVo departmentPutVo
    ) {

        try {
            if (departmentService.update(id, departmentPutVo)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (ParamException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "删除科室", notes = "删除科室")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String modifyDepartment(@ApiParam(value = "科室ID") @PathVariable(value = "id") String id

    ) {

        try {
            if (departmentService.delete(id)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (ParamException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }


}
