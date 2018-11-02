package com.mujugroup.core.controller;


import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.objeck.vo.dictDepartment.AddVo;
import com.mujugroup.core.objeck.vo.dictDepartment.PutVo;
import com.mujugroup.core.service.DictDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/dictDepartment")
@Api(description = "木巨科室操作相关接口")
public class DictDepartmentController {

    private DictDepartmentService dictDepartmentService;

    @Autowired
    public DictDepartmentController(DictDepartmentService dictDepartmentService) {
        this.dictDepartmentService = dictDepartmentService;
    }

    @ApiOperation(value = "查询木巨科室下拉列表", notes = "查询木巨科室下拉列表，可模糊匹配木巨名字")
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String getList(@ApiParam(value = "模糊名称", required = false) @RequestParam(value = "name", required = false, defaultValue = "") String name) throws BaseException {
        List<SelectVO> list =dictDepartmentService.getDictDepartmentList(name);
        if (list!=null&&list.size()>0){
            return ResultUtil.success(list);
        }else {
            return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        }

    }

    @ApiOperation(value = "添加木巨科室", notes = "添加木巨科室")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addDictDepartment(@ApiParam(hidden = true) int uid
            , @Validated @ModelAttribute AddVo dictDepartmentVo) throws BaseException {
        if (dictDepartmentService.add(uid, dictDepartmentVo)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

    @ApiOperation(value = "修改木巨科室", notes = "修改木巨科室")
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public String modifyDictDepartment(@ApiParam(hidden = true) int uid
            , @Validated @ModelAttribute PutVo dictDepartmentPutVo) throws BaseException {
        if (dictDepartmentService.update(uid, dictDepartmentPutVo)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

    @ApiOperation(value = "删除木巨科室", notes = "删除木巨科室")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String removeDictDepartment(@PathVariable(value = "id") String id) throws BaseException {
        if (dictDepartmentService.delete(id)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

}
