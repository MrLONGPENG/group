package com.mujugroup.lock.controller;


import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.lock.objeck.bo.fail.FailBo;
import com.mujugroup.lock.objeck.vo.fail.PutVo;
import com.mujugroup.lock.service.LockFailService;
import com.mujugroup.lock.service.feign.ModuleCoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/fail")
@Api(description = "故障数量接口")
public class LockFailController {

    private LockFailService lockFailService;
    private ModuleCoreService moduleCoreService;

    @Autowired
    public LockFailController(LockFailService lockFailService, ModuleCoreService moduleCoreService) {
        this.lockFailService = lockFailService;
        this.moduleCoreService = moduleCoreService;
    }


    @ApiOperation(value = "获取故障数量", notes = "获取故障数量")
    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public String getFailCount(@ApiParam(hidden = true) String uid) throws BaseException {
        return ResultUtil.success(lockFailService.getFailCount(uid));
    }


    @ApiOperation(value = "获取异常", notes = "获取异常")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String getFailInfoList(
            @ApiParam(value = "当前页") @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum
            , @ApiParam(value = "每页显示") @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
            , @ApiParam(hidden = true) String uid
            , @ApiParam(value = "异常类型") @RequestParam(value = "type", required = false, defaultValue = "0") int type
    ) throws BaseException {
        Map<String, String> map = moduleCoreService.getAuthData(uid);
        List<FailBo> list = lockFailService.getFailInfoList(map, pageNum, pageSize, type);
        return ResultUtil.success(lockFailService.toFailVo(list), PageInfo.of(list));
    }

    @ApiOperation(value = "巡查反馈", notes = "巡查反馈")
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    public String feedbackFail(@ModelAttribute PutVo putVo) throws BaseException{
        if (lockFailService.modify(putVo)){
            return ResultUtil.success();
        }else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

}
