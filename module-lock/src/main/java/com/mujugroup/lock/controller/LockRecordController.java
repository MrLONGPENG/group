package com.mujugroup.lock.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.lock.objeck.vo.record.ListVo;
import com.mujugroup.lock.service.LockRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/record")
@Api(description = "锁记录接口")
public class LockRecordController {

    private LockRecordService lockRecordService;

    @Autowired
    public LockRecordController(LockRecordService lockRecordService) {
        this.lockRecordService = lockRecordService;
    }

    @ApiOperation(value = "/获取记录列表数据", notes = "获取记录列表数据")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(@ModelAttribute ListVo listVo) {
        PageHelper.startPage(listVo.getPageNum(), listVo.getPageSize());
        List<ListVo> list = lockRecordService.getRecordList(listVo);
        return ResultUtil.success(list, PageInfo.of(list));
    }


}
