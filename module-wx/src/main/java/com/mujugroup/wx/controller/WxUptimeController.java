package com.mujugroup.wx.controller;


import com.lveqia.cloud.common.ResultUtil;
import com.mujugroup.wx.exception.ParamException;
import com.mujugroup.wx.model.WxRelation;
import com.mujugroup.wx.model.WxUptime;
import com.mujugroup.wx.service.WxUptimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author leolaurel
 */
@RestController
@RequestMapping("/uptime")
@Api(description="医院开锁时间业务接口")
public class WxUptimeController {
    private final Logger logger = LoggerFactory.getLogger(WxUptimeController.class);
    private WxUptimeService wxUptimeService;

    @Autowired
    public WxUptimeController(WxUptimeService wxUptimeService) {
        this.wxUptimeService = wxUptimeService;
    }


    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ApiOperation(value="医院开锁时间查询接口", notes="根据类型查询医院开锁时间,未找到返回默认数据")
    public String find(@ApiParam(value="时间类型(2:运行时间 3:午休时间)", required = true)@RequestParam(name="type"
            , defaultValue = "2") int type, @ApiParam(value="代理商ID", required = true) @RequestParam(name="aid")
            int aid,@ApiParam(value="医院ID", required = true) @RequestParam(name="hid") int hid ,@ApiParam(value=
            "科室ID" , required = true) @RequestParam(name="oid") int oid) {
        logger.debug("find type:{} aid:{} hid:{} oid:{}", type, aid, hid, oid);
        if(type!= WxRelation.TYPE_UPTIME  && type != WxRelation.TYPE_MIDDAY){
            return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT, "Type只能为2:运行时间 3:午休时间");
        }
        WxUptime wxUptime = wxUptimeService.findByXid(new int[]{0, aid, hid, oid}, type);
        if (wxUptime != null) {
            return ResultUtil.success(wxUptime);
        }else {
            return ResultUtil.success(wxUptimeService.getDefaultWxUptime(type));
        }
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ApiOperation(value="医院开锁时间查询接口", notes="根据类型查询医院开锁时间,未找到返回空")
    public String query(@ApiParam(value="时间类型(2:运行时间 3:午休时间)", required = true)@RequestParam(name="type"
            , defaultValue = "2") int type, @ApiParam(value="外键类型(0:默认数据 1:代理商 2:医院 3:科室 4:其他)"
            , required = true) @RequestParam(name="key") int key, @ApiParam(value="外键ID"
            , required = true) @RequestParam(name="kid") int kid){
        logger.debug("query type:{} key:{} kid:{}", type, key, kid);
        if(type!= WxRelation.TYPE_UPTIME  && type != WxRelation.TYPE_MIDDAY){
            return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT, "Type只能为2:运行时间 3:午休时间");
        }
        WxUptime wxUptime = wxUptimeService.query(type, key, kid);
        if(wxUptime != null){
            return ResultUtil.success(wxUptime);
        }
        return ResultUtil.error(ResultUtil.CODE_NOT_FIND_DATA);
    }

    @ApiOperation(value="医院开锁时间新增或更新接口", notes="根据类型(代理商;医院;科室)更新医院开锁时间")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ApiParam(value="时间类型(2:运行时间 3:午休时间)", required = true)@RequestParam(name="type"
            , defaultValue = "2") int type, @ApiParam(value="外键类型(0:默认数据 1:代理商 2:医院 3:科室 4:其他)"
            , required = true) @RequestParam(name="key") int key, @ApiParam(value="外键ID", required = true)
            @RequestParam(name="kid") int kid, @ApiParam(value="开始时间，格式hh:mm", required = true)
            @RequestParam(name="startDesc") String startDesc, @ApiParam(value="结束时间，格式hh:mm"
            , required = true)  @RequestParam(name="stopDesc") String stopDesc, @ApiParam(value="此属性使用说明" +
            ", 非必须") @RequestParam(name="explain", required = false) String explain) {
        try {
            if(wxUptimeService.update(type, key, kid, startDesc, stopDesc, explain)){
                return ResultUtil.success();
            }else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        }catch (NumberFormatException e){
            return ResultUtil.error(ResultUtil.CODE_REQUEST_FORMAT);
        } catch (ParamException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation(value="医院开锁时间删除接口", notes="根据类型(代理商;医院;科室)删除医院开锁时间")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@ApiParam(value="时间类型(2:运行时间 3:午休时间)", required = true)@RequestParam(name="type"
            , defaultValue = "2") int type, @ApiParam(value="外键类型(0:默认数据 1:代理商 2:医院 3:科室 4:其他)"
            , required = true) @RequestParam(name="key") int key, @ApiParam(value="外键ID"
            , required = true) @RequestParam(name="kid") int kid) {
        try {
            if(wxUptimeService.delete(type, key, kid)){
                return ResultUtil.success();
            }else {
                return ResultUtil.error(ResultUtil.CODE_DB_STORAGE_FAIL);
            }
        } catch (ParamException e) {
            return ResultUtil.error(e.getCode(), e.getMessage());
        }
    }
}
