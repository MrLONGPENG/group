package com.mujugroup.data.controller;

import com.lveqia.cloud.common.DateUtil;
import com.lveqia.cloud.common.ResultUtil;
import com.lveqia.cloud.common.StringUtil;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.Constant;
import com.mujugroup.data.service.ExcelService;
import com.mujugroup.data.service.StatisticsService;
import com.mujugroup.data.service.feign.ModuleCoreService;
import com.mujugroup.data.utils.ExcelData;
import com.mujugroup.data.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/statistics")
@Api(description="数据模块数据统计接口")
public class StatisticsController {
    private final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    private final ExcelService excelService;
    private final StatisticsService statisticsService;
    private final ModuleCoreService moduleCoreService;


    @Autowired
    public StatisticsController(ExcelService excelService, StatisticsService statisticsService
            , ModuleCoreService moduleCoreService) {
        this.excelService = excelService;
        this.statisticsService = statisticsService;
        this.moduleCoreService = moduleCoreService;
    }

    @ApiOperation(value="查询木巨柜激活、使用和收益情况", notes="根据代理商、医院、科室查询木巨柜使用、收益情况")
    @RequestMapping(value = "/chart",method = RequestMethod.POST)
    public String chart(@ApiParam(value="代理商ID") @RequestParam(name="aid", defaultValue="0") int aid
            , @ApiParam(value="医院ID")@RequestParam(name="hid", required=false, defaultValue="0") int hid
            , @ApiParam(value="科室ID")@RequestParam(name="oid", required=false, defaultValue="0") int oid
            , @ApiParam(value="省份ID")@RequestParam(name="pid", required=false, defaultValue="0") int pid
            , @ApiParam(value="城市ID")@RequestParam(name="cid", required=false, defaultValue="0") int cid
            , @ApiParam(value="开始时间戳(秒)", required = true) @RequestParam(name="startTime") int start
            , @ApiParam(value="结束时间戳(秒)", required = true) @RequestParam(name="stopTime") int stop
            , @ApiParam(value="查询动作", required = true) @RequestParam(name="action") String action
            , @ApiParam(value="粒度类型(1:日 2:周 3:月) 默认日") @RequestParam(name="grain", required=false
            , defaultValue="1") int grain) {
        logger.debug("active {} {} {} {} {}", aid, hid, oid, grain, action);
        String ids = null ;
        if(pid != 0 || cid != 0){ // 优先根据城市查询医院ID集合
            Set<Integer> set =  moduleCoreService.getHospitalByRegion(pid, cid);
            if(set != null && set.size() > 0 ) ids = StringUtil.join(Constant.SIGN_LINE,  set.toArray());
        }
        try {
            switch (action){
                case "getStaUsage": case "get_statistics_usage" :
                    return ResultUtil.success(statisticsService.getUsage(ids, aid, hid, oid, grain, start, stop));
                case "getStaActive" : case "get_statistics_active" :
                    return ResultUtil.success(statisticsService.getActive(ids, aid, hid, oid, grain, start, stop));
                case "getStaProfit" : case "get_statistics_profit" :
                    return ResultUtil.success(statisticsService.getProfit(ids, aid, hid, oid, grain, start, stop));
                case "getStaUsageRate": case "get_statistics_usage_rate" :
                    return ResultUtil.success(statisticsService.getUsageRate(ids, aid, hid, oid, grain, start, stop));
            }
        } catch (ParamException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
        return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT, "无法找到Action:"+action);
    }


    @ApiOperation(value="导出Excel数据问题", notes="根据代理商、医院、科室查询木巨柜使用、收益情况")
    @RequestMapping(value = "/excel",method = RequestMethod.GET)
    public void excel(@ApiParam(value="代理商ID", required = true) @RequestParam(name="aid") int aid
            , @ApiParam(value="医院ID")@RequestParam(name="hid", required=false, defaultValue="0") int hid
            , @ApiParam(value="开始时间戳(秒)", required = true) @RequestParam(name="startTime") int startTime
            , @ApiParam(value="结束时间戳(秒)", required = true) @RequestParam(name="stopTime") int stopTime
            , @ApiParam(value="粒度类型(1:日 2:周 3:月) 默认日") @RequestParam(name="grain", required=false
            , defaultValue="1") int grain, HttpServletResponse response) throws Exception {
        long time = System.currentTimeMillis();
        List<ExcelData> list = excelService.getExcelDataList(aid, hid,grain, startTime, stopTime);
        ExcelUtils.exportExcel(response, StringUtil.join("", DateUtil.timestampToDays(startTime)
                , "-", DateUtil.timestampToDays(stopTime), ".xlsx"), list);
        logger.debug("导出Excel花费{}毫秒", System.currentTimeMillis() - time);
    }


}
