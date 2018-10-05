package com.mujugroup.data.controller;

import com.google.gson.JsonObject;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.util.ResultUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.config.Constant;
import com.mujugroup.data.service.ExcelService;
import com.mujugroup.data.service.StaBOService;
import com.mujugroup.data.service.StaVOService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/statistics")
@Api(description="数据模块数据统计接口")
public class StatisticsController {
    private final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    private final ExcelService excelService;
    private final StaVOService staVOService;
    private final StaBOService staBOService;
    private final ModuleCoreService moduleCoreService;

    @Autowired
    public StatisticsController(ExcelService excelService, StaVOService staVOService
            , StaBOService staBOService, ModuleCoreService moduleCoreService) {
        this.excelService = excelService;
        this.staVOService = staVOService;
        this.staBOService = staBOService;
        this.moduleCoreService = moduleCoreService;
    }

    @ApiOperation(value="查询木巨柜激活、使用和收益情况", notes="根据代理商、医院、科室查询木巨柜使用、收益情况")
    @RequestMapping(value = "/chart",method = RequestMethod.POST)
    public String chart(@ApiParam(value="代理商ID") @RequestParam(name="aid", defaultValue="0") String aid
            , @ApiParam(value="医院ID")@RequestParam(name="hid", required=false, defaultValue="0") String hid
            , @ApiParam(value="科室ID")@RequestParam(name="oid", required=false, defaultValue="0") String oid
            , @ApiParam(value="省份ID")@RequestParam(name="pid", required=false, defaultValue="0") int pid
            , @ApiParam(value="城市ID")@RequestParam(name="cid", required=false, defaultValue="0") int cid
            , @ApiParam(value="开始时间戳(秒)", required = true) @RequestParam(name="startTime") int start
            , @ApiParam(value="结束时间戳(秒)", required = true) @RequestParam(name="stopTime") int stop
            , @ApiParam(value="查询动作{getStaUsage->使用情况;getStaActive->激活情况;getStaProfit->收益情况;" +
            "getStaUsageRate:使用率情况}", required = true) @RequestParam(name="action") String action
            , @ApiParam(value="粒度类型(1:日 2:周 3:月) 默认日") @RequestParam(name="grain", required=false
            , defaultValue="1") int grain, @ApiParam(hidden = true) String uid) {
        logger.debug("active {} {} {} {} {}", aid, hid, oid, grain, action);
        try {
            List<?> list = staVOService.getStaVOList(action, uid, pid, cid, aid, hid, oid, grain, start, stop);
            if(list == null) return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
            return ResultUtil.success(list);
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value="按医院标签展示表格数据", notes="根据代理商、医院、科室查询木巨柜使用、收益情况")
    @RequestMapping(value = "/table",method = RequestMethod.POST)
    public String table(@ApiParam(value="医院ID", required = true) @RequestParam(name="hid") String hid
            //, @ApiParam(value="省份ID")@RequestParam(name="pid", required=false, defaultValue="0") int pid
            //, @ApiParam(value="城市ID")@RequestParam(name="cid", required=false, defaultValue="0") int cid
            , @ApiParam(value="开始时间戳(秒)", required = true) @RequestParam(name="startTime") int startTime
            , @ApiParam(value="结束时间戳(秒)", required = true) @RequestParam(name="stopTime") int stopTime
            , @ApiParam(value="粒度类型(1:日 2:周 3:月) 默认日") @RequestParam(name="grain", required=false
            , defaultValue="1") int grain, @ApiParam(hidden = true) String uid){
        long time = System.currentTimeMillis();
        try {
            return ResultUtil.success(staVOService.getExcelVO(uid, hid, grain, startTime, stopTime));
        } catch (BaseException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        } finally {
            logger.debug("查询Table花费{}毫秒", System.currentTimeMillis() - time);
        }
    }


    @ApiOperation(value="导出Excel数据问题", notes="根据代理商、医院、科室查询木巨柜使用、收益情况")
    @RequestMapping(value = "/excel",method = RequestMethod.GET)
    public void excel(@ApiParam(value="医院ID")@RequestParam(name="hid", required=false) String[] hid
            //, @ApiParam(value="省份ID")@RequestParam(name="pid", required=false, defaultValue="0") int pid
            //, @ApiParam(value="城市ID")@RequestParam(name="cid", required=false, defaultValue="0") int cid
            , @ApiParam(value="开始时间戳(秒)", required = true) @RequestParam(name="startTime") int startTime
            , @ApiParam(value="结束时间戳(秒)", required = true) @RequestParam(name="stopTime") int stopTime
            , @ApiParam(value="粒度类型(1:日 2:周 3:月) 默认日") @RequestParam(name="grain", required=false
            , defaultValue="1") int grain, HttpServletResponse response, HttpServletRequest request) throws Exception {

        logger.debug("session id {}", request.getSession());
        logger.debug("excel {} {} {} {} {}", hid, grain);
        long time = System.currentTimeMillis();
        List<ExcelData> list = excelService.getExcelDataList(hid, grain, startTime, stopTime);
        ExcelUtils.exportExcel(response, StringUtil.join("", DateUtil.timestampToDays(startTime)
                , "-", DateUtil.timestampToDays(stopTime), ".xlsx"), list);
        logger.debug("导出Excel花费{}毫秒", System.currentTimeMillis() - time);
    }





}
