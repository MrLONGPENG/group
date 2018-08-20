package com.mujugroup.data.controller;

import com.lveqia.cloud.common.DateUtil;
import com.lveqia.cloud.common.ResultUtil;
import com.mujugroup.data.bean.ActiveBean;
import com.mujugroup.data.service.StatisticsService;
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

import java.util.List;


@RestController
@RequestMapping("/statistics")
@Api(description="数据模块数据统计接口")
public class StatisticsController {
    private final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    private final StatisticsService statisticsService;


    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @ApiOperation(value="查询木巨柜激活情况", notes="根据代理商、医院、科室查询木巨柜激活情况")
    @RequestMapping(value = "/active",method = RequestMethod.POST)
    public String active(@ApiParam(value="代理商ID", required = true) @RequestParam(name="aid") int aid
            , @ApiParam(value="医院ID")@RequestParam(name="hid", required=false, defaultValue="0") int hid
            , @ApiParam(value="科室ID")@RequestParam(name="oid", required=false, defaultValue="0") int oid
            , @ApiParam(value="开始时间戳(秒)", required = true) @RequestParam(name="startTime") int startTime
            , @ApiParam(value="结束时间戳(秒)", required = true) @RequestParam(name="stopTime") int stopTime
            , @ApiParam(value="查询动作", required = true) @RequestParam(name="action") String action
            , @ApiParam(value="粒度类型(1:日 2:周 3:月) 默认日") @RequestParam(name="grain", required=false
            , defaultValue="1") int grain) {
        logger.debug("active {} {} {} {} {}", aid, hid, oid, grain, action);
        long morning = DateUtil.getTimesMorning();
        if(startTime > morning || stopTime > morning || startTime == stopTime)return ResultUtil.code(
                ResultUtil.CODE_REQUEST_FORMAT , "开始结束时间戳不能相等且不能大于当天凌晨");
        List<ActiveBean> list = statisticsService.activeStatistics(aid, hid, oid, grain, startTime, stopTime);
        if(list!=null && list.size() > 0 ) return ResultUtil.success(list);
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

}
