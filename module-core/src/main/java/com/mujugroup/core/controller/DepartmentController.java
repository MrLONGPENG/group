package com.mujugroup.core.controller;

import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.DepartmentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @ApiOperation(value = "通过选中的医院ID获取所有的科室", notes = "通过选中的医院ID获取所有的科室")
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public String list(@ApiParam(value = "医院ID") @RequestParam(value = "hid") String hid) {
        List<SelectVO> departmentSelectVoList = departmentService.getListByHid(hid);
        if (departmentSelectVoList!=null&&departmentSelectVoList.size()>0){
            return ResultUtil.success(departmentSelectVoList);
        }else{
            return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        }
    }
}
