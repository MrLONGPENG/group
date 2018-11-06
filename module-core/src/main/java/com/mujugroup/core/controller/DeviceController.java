package com.mujugroup.core.controller;


import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.objeck.to.PageTO;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.core.model.Device;
import com.mujugroup.core.objeck.vo.device.AddVo;
import com.mujugroup.core.objeck.vo.device.PutVo;
import com.mujugroup.core.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/device")
@Api(description = "设备激活情况相关接口")
public class DeviceController {
    private final Logger logger = LoggerFactory.getLogger(DeviceController.class);
    private DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @ApiOperation(value = "获取设备列表", notes = "根据条件获取分页数据")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@ApiParam(value = "当前页") @RequestParam(name = "pageNum", required = false
            , defaultValue = "1") int pageNum, @ApiParam(value = "每页显示") @RequestParam(name = "pageSize"
            , required = false, defaultValue = "10") int pageSize, @ApiParam(value = "指定状态查询")
                       @RequestParam(name = "status", required = false, defaultValue = "0") int status
            , @RequestParam(name = "did", required = false, defaultValue = "") String did
            , @RequestParam(name = "bid", required = false, defaultValue = "") String bid
            , @RequestParam(name = "bed", required = false, defaultValue = "") String bed
            , @RequestParam(name = "aid", required = false, defaultValue = "") String aid
            , @RequestParam(name = "hid", required = false, defaultValue = "") String hid
            , @RequestParam(name = "oid", required = false, defaultValue = "") String oid
    ) {
        logger.debug("device-list");
        PageTO<Device> page = deviceService.findDeviceList(did, bid, bed, aid, hid, oid, status, pageNum, pageSize);
        return ResultUtil.success(deviceService.toDeviceBO(page.getPageList()), page.getPageInfo());
    }

    @ApiOperation(value = "设备激活", notes = "设备激活")
    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public String activation(@ApiParam(hidden = true) int uid, @Validated @ModelAttribute AddVo deviceVo)
            throws ParamException {
        if (deviceService.insert(uid, deviceVo)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

    @ApiOperation(value = "编辑设备信息", notes = "编辑设备信息")
    @RequestMapping(value = "/put", method = RequestMethod.PUT)
    public String modifyDevice(@ApiParam(hidden = true) int uid, @Validated @ModelAttribute PutVo devicePutVo)
            throws ParamException {
        if (deviceService.modifyDevice(uid, devicePutVo)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }

    @ApiOperation(value = "删除设备", notes = "删除设备")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String deleteDevice(@ApiParam(value = "选中的设备ID") @PathVariable(value = "id") String id)
            throws ParamException {
        if (deviceService.delete(id)) {
            return ResultUtil.success();
        } else {
            return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
        }
    }
}
