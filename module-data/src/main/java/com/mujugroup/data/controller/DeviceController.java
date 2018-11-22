package com.mujugroup.data.controller;

import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.to.InfoTo;
import com.lveqia.cloud.common.objeck.to.SelectTo;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.data.objeck.bo.ListBo;
import com.mujugroup.data.objeck.vo.DeviceVo;
import com.mujugroup.data.objeck.vo.ListVo;
import com.mujugroup.data.objeck.vo.device.InfoVo;
import com.mujugroup.data.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
@Api(description = "数据模块设备接口")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @ApiOperation(value = "获取设备详情", notes = "根据条件获取设备详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String getDeviceDetailByDid(@ApiParam(value = "业务编号") @RequestParam(value = "did") String did) throws BaseException {
        DeviceVo deviceVo = deviceService.getDeviceDetailByDid(did);
        return ResultUtil.success(deviceVo);
    }

    @ApiOperation(value = "获取使用率", notes = "获取使用率")
    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    public String getUsageRate(@ApiParam(hidden = true) int uid, @ModelAttribute ListVo listVo) throws BaseException {
        PageInfo<SelectTo> selectToList = deviceService.getSelectVo(uid, listVo.getId() == null ? 0 : listVo.getId(), listVo.getPageNum(), listVo.getPageSize());
        List<ListBo> list = deviceService.getUsageRate(listVo.getId() == null ? 0 : listVo.getId(), selectToList.getList());
        return ResultUtil.success(list, selectToList);
    }

    @ApiOperation(value = "获取设备数据", notes = "获取设备数据")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public String getInfoByDid(@ApiParam(value = "科室ID") @RequestParam(value = "id") String id
            , @ApiParam(value = "当前页") @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum
            , @ApiParam(value = "每页显示") @RequestParam(name = "pageSize"
            , required = false, defaultValue = "10") int pageSize) throws ParamException {
        PageInfo<InfoTo> pageInfo = deviceService.infoVoList(id, pageNum, pageSize);
        List<InfoVo> list=deviceService.boToVo(deviceService.getInfoById(pageInfo));
        return ResultUtil.success(list, pageInfo);
    }
}
