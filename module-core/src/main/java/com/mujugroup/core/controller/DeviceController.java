package com.mujugroup.core.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.model.Device;
import com.mujugroup.core.objeck.bean.DeviceBean;
import com.mujugroup.core.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/device")
@Api(description="设备激活情况相关接口")
public class DeviceController {
    private final Logger logger = LoggerFactory.getLogger(DeviceController.class);
    private DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @ApiOperation(value="查询设备信息", notes="根据DID查询指定设备信息")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public String query(String did) {
        logger.info("device--query");
        DeviceBean bean =  deviceService.findDeviceBeanByDid(did);
        if(bean!=null){
            return ResultUtil.success(bean);
        }
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }


    @ApiOperation(value="获取设备列表", notes="根据条件获取分页数据")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST })
    public String list(@ApiParam(value="当前页")@RequestParam(name="pageNum", required=false
            , defaultValue="1") int pageNum, @ApiParam(value="每页显示")@RequestParam(name="pageSize"
            , required=false, defaultValue="10") int pageSize, @ApiParam(value="指定状态查询")
            @RequestParam(name="status", required=false, defaultValue="0") int status){
        logger.debug("device-list");
        PageHelper.startPage(pageNum, pageSize);
        List<Device> list = status==0 ? deviceService.findListAll() : deviceService.findListByStatus(status);
        if(list!=null){
            return ResultUtil.success(list, PageInfo.of(list));
        }
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }
}
