package com.mujugroup.data.controller;

import com.lveqia.cloud.common.DateUtil;
import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.bean.OverviewInfo;
import com.mujugroup.data.service.OverviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/overview")
@Api(description="数据模块概览接口")
public class OverviewController {
    private final Logger logger = LoggerFactory.getLogger(OverviewController.class);

    private final OverviewService overviewService;

    @Autowired
    public OverviewController(OverviewService overviewService) {
        this.overviewService = overviewService;
    }

    @ApiOperation(value="查询概览数据(不包含当日数据)", notes="根据条件查询概览数据(已激活数，总用户数，昨日使用数)")
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public String info(@ApiParam(value="代理商ID，无ID或无ID即查询全部代理商") @RequestParam(name="aid"
            , defaultValue=Constant.DIGIT_ZERO) int aid, @ApiParam(value="概览统计时间戳(秒)，时间戳为0或为空" +
            "，默认按当前时间计算") @RequestParam(name="timestamp", defaultValue=Constant.DIGIT_ZERO) long timestamp){
        logger.debug("active {} {}", aid, timestamp);
        long morning = DateUtil.getTimesMorning();
        if(timestamp == 0 || timestamp > morning ) {
            timestamp = morning;
        }else { // 采用东八区（北京时间）计算
            long offset = timestamp % Constant.TIMESTAMP_DAYS_1;
            if(offset > 0) timestamp -= offset + Constant.TIMESTAMP_HOUR_8;
        }
        OverviewInfo overviewInfo = overviewService.info(aid, timestamp);
        if(overviewInfo!=null) return ResultUtil.success(overviewInfo);
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

}
