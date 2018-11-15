package com.mujugroup.lock.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.objeck.to.PageTo;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.lock.objeck.bo.fail.FailBo;
import com.mujugroup.lock.service.LockFailService;
import com.mujugroup.lock.service.feign.ModuleCoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public String feedbackFail(@ApiParam(value = "业务编号") @RequestParam(value = "did") String did
            , @ApiParam(value = "故障解决状态(3:已解决 4:未解决)") @RequestParam(value = "type", defaultValue = "4") int type
            , @ApiParam(value = "维修人") @RequestParam(value = "uid") int uid
            , @ApiParam(value = "故障编码") @RequestParam(value = "failCode") String failCode
            , @ApiParam(value = "错误编码") @RequestParam(value = "errorCode") String errorCode
            , @ApiParam(value = "异常产生原因及解决方法") @RequestParam(value = "explain", required = false, defaultValue = "") String explain
    ) throws BaseException{

        if (lockFailService.modify(uid,type,did,failCode,errorCode,explain)){
            return ResultUtil.success();
        }else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

}
