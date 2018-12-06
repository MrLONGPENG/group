package com.mujugroup.wx.controller;

import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.wx.model.WxDeposit;
import com.mujugroup.wx.objeck.vo.deposit.InfoListVo;
import com.mujugroup.wx.objeck.vo.deposit.PutVo;
import com.mujugroup.wx.service.WxDepositService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Map;


/**
 * @author leolaurel
 */

@RestController
@Api(description = "押金接口")
public class WxDepositController {

    private WxDepositService wxDepositService;

    @Autowired
    public WxDepositController(WxDepositService wxDepositService) {
        this.wxDepositService = wxDepositService;
    }

    @ApiOperation(value = "获取当前用户已支付状态的押金信息", notes = "获取当前用户已支付状态的押金信息")
    @RequestMapping(value = "/deposit/info", method = RequestMethod.POST)
    public String getDepositInfo(@ApiParam(value = "sessionThirdKey", required = true)
                                 @RequestParam(value = "sessionThirdKey") String sessionThirdKey) {
        return ResultUtil.success(wxDepositService.getDepositInfo(sessionThirdKey));
    }

    @ApiOperation(value = "修改押金状态", notes = "申请退款时,修改当前用户的押金状态")
    @RequestMapping(value = "/deposit/refund", method = RequestMethod.PUT)
    public String modifyDepositStatus(@ApiParam(value = "sessionThirdKey", required = true)
                                      @RequestParam(value = "sessionThirdKey") String sessionThirdKey
            , @ApiParam(value = "当前选中的押金信息ID") @RequestParam(value = "id") long id) throws BaseException {
        return ResultUtil.success(wxDepositService.modifyStatus(sessionThirdKey, id));
    }

    @ApiOperation(value = "押金列表", notes = "获取所有状态押金列表，优先显示退款中的状态")
    @RequestMapping(value = "/audit/list", method = RequestMethod.POST)
    public String getRefundingList() {
        return ResultUtil.success(wxDepositService.getInfoList());
    }

    @ApiOperation(value = "修改押金状态为审核通过以及其他记录表状态,插入退款记录表"
            , notes = "修改押金状态为审核通过以及其他记录表状态,插入退款记录表")
    @RequestMapping(value = "/audit/deposit", method = RequestMethod.PUT)
    public String modifyStatus(@Validated @ModelAttribute PutVo infoVo) throws BaseException {
        return ResultUtil.success(wxDepositService.modifyRecordStatus(infoVo));
    }

}
