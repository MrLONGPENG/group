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

import java.util.List;


/**
 * @author leolaurel
 */
@Api(description = "押金接口")
@RestController
@RequestMapping("/deposit")
public class WxDepositController {

    private WxDepositService wxDepositService;

    @Autowired
    public WxDepositController(WxDepositService wxDepositService) {
        this.wxDepositService = wxDepositService;
    }

    @ApiOperation(value = "获取当前用户已支付状态的押金信息", notes = "获取当前用户已支付状态的押金信息")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public String getDepositInfo(@ApiParam(value = "sessionThirdKey") @RequestParam(value = "sessionThirdKey") String sessionThirdKey
            , @ApiParam(value = "code") @RequestParam(value = "code") String code) throws BaseException {
        WxDeposit wxDeposit = wxDepositService.getDepositInfo(sessionThirdKey, code);
        return ResultUtil.success(wxDeposit);
    }

    @ApiOperation(value = "修改押金状态", notes = "申请退款时,修改当前用户的押金状态")
    @RequestMapping(value = "modify", method = RequestMethod.PUT)
    public String modifyDepositStatus(@ApiParam(value = "sessionThirdKey") @RequestParam(value = "sessionThirdKey") String sessionThirdKey
            , @ApiParam(value = "code") @RequestParam(value = "code") String code
            , @ApiParam(value = "当前选中的押金信息ID") @RequestParam(value = "id") long id) throws BaseException {
        boolean result = wxDepositService.modifyStatus(sessionThirdKey, code, id);
        return ResultUtil.success(result);
    }

    @ApiOperation(value = "押金列表", notes = "获取所有状态押金列表，优先显示退款中的状态")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String getRefundingList() {
        List<InfoListVo> list = wxDepositService.getInfoList();
        return ResultUtil.success(list);
    }

}
