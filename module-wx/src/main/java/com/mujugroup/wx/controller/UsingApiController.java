package com.mujugroup.wx.controller;

import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.exception.TokenException;
import com.mujugroup.wx.service.UsingApiService;
import com.mujugroup.wx.service.WxUsingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/using")
@Api(description="小程序业务接口")
public class UsingApiController {
    private final Logger logger = LoggerFactory.getLogger(UsingApiController.class);
    private final UsingApiService usingApiService;
    private final WxUsingService wxUsingService;

    @Autowired
    public UsingApiController(UsingApiService usingApiService, WxUsingService wxUsingService) {
        this.usingApiService = usingApiService;
        this.wxUsingService = wxUsingService;
    }

    @ApiOperation(value="设备状态检查接口", notes="查询锁设备状态")
    @RequestMapping(value = "/check", method = {RequestMethod.GET, RequestMethod.POST })
    public String check(String sessionThirdKey, String did){
        return ResultUtil.success(usingApiService.checkUsing(sessionThirdKey, did));
    }

    @ApiOperation(value="开锁接口", notes="符合指定条件直接开锁")
    @RequestMapping(value = "/unlock", method = {RequestMethod.GET, RequestMethod.POST })
    public String unlock(String sessionThirdKey, String did, String code){
        String[] arr = usingApiService.parseCode(sessionThirdKey, code);
        if(arr == null ) return ResultUtil.error(ResultUtil.CODE_VALIDATION_FAIL);
        return ResultUtil.success(usingApiService.unlock(did, arr));
    }


    @ApiOperation(value="开锁时间接口", notes="查询锁设备开关锁时间范围")
    @RequestMapping(value = "/uptime", method = {RequestMethod.GET, RequestMethod.POST })
    public String uptime(String sessionThirdKey, String code) {
        String[] arr = usingApiService.parseCode(sessionThirdKey, code);
        if (arr == null) return ResultUtil.error(ResultUtil.CODE_VALIDATION_FAIL);
        return ResultUtil.success(usingApiService.uptime(arr));
    }

    @ApiOperation(value="开锁状态查询接口", notes="查询锁设备是否开锁")
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST })
    public String query(String sessionThirdKey, String did, String code, boolean isSync){
        try {
            return ResultUtil.success(usingApiService.query(sessionThirdKey, did, code, isSync));
        } catch (TokenException e) {
            return ResultUtil.code(e.getCode());
        }
    }


    @RequestMapping(value = "/delete")
    public String delete(String password, String did){
        logger.info("wx-using-delete did:{}", did);
        if(!"leo".equals(password)) return ResultUtil.success("密码错误");
        return  ResultUtil.success(wxUsingService.deleteByDid(did
                , System.currentTimeMillis()/1000)?"删除成功":"无数据删");
    }

    @RequestMapping(value = "/notify")
    public String notify(String bid, Integer lockStatus){
        logger.info("wx-using-notify bid:{} lockStatus:{}", bid, lockStatus);
        usingApiService.notify(bid, lockStatus);
        return ResultUtil.success();
    }
}
