package com.mujugroup.lock.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.lock.model.LockInfo;
import com.mujugroup.lock.objeck.vo.info.ListVo;
import com.mujugroup.lock.service.LockInfoService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/info")
public class LockInfoController {

    private LockInfoService lockInfoService;
    private final Logger logger = LoggerFactory.getLogger(LockInfoController.class);

    @Autowired
    public LockInfoController(LockInfoService lockInfoService) {
        this.lockInfoService = lockInfoService;
    }

    @RequestMapping(value = "/query")
    public String query(String bid) {
        logger.debug("info->query bid:{}", bid);
        if (bid == null) return ResultUtil.error(ResultUtil.CODE_PARAMETER_MISS);
        if (bid.length() > 19) return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT);
        LockInfo lockInfo = lockInfoService.getLockInfoByBid(bid);
        if (lockInfo == null) return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
        return ResultUtil.success(lockInfo);
    }

    @ApiOperation(value = "获取设备信息列表", notes = "获取设备信息列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String query(@ModelAttribute ListVo listVo) {
        PageHelper.startPage(listVo.getPageNum(), listVo.getPageSize());
        List<ListVo> list = lockInfoService.getInfoList(listVo);
        return ResultUtil.success(list,PageInfo.of(list));
    }

}
