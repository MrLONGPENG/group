package com.mujugroup.core.controller;


import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.objeck.vo.SelectVO;
import com.mujugroup.core.service.RegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 地区-国家、省份、城市 操作
 * @author leolaurel
 */
@RestController
@RequestMapping("/region")
@Api(description="地区（国家、省份、城市）相关接口")
public class RegionController {

    private RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @ApiOperation(value="查询地区列表", notes="根据父ID查询子地区")
    @RequestMapping(value = "/child", method = RequestMethod.POST)
    public String child(@ApiParam(value="父ID(PID),默认0,即查询国家", required = true)@RequestParam(name="pid"
            , required=false, defaultValue="0") int pid){
        List<SelectVO> list = regionService.getRegionByPid(pid);
        if(list!=null) return ResultUtil.success(list);
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

}
