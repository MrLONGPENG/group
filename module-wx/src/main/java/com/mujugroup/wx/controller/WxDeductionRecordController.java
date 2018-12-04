package com.mujugroup.wx.controller;


import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.wx.model.WxDeductionRecord;
import com.mujugroup.wx.service.WxDeductionRecordService;
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
 * @author leolaurel
 */
@Api("扣款")
@RestController
@RequestMapping("/deduction")
public class WxDeductionRecordController {

    private WxDeductionRecordService wxDeductionRecordService;

    @Autowired
    public WxDeductionRecordController(WxDeductionRecordService wxDeductionRecordService) {
        this.wxDeductionRecordService = wxDeductionRecordService;
    }

    @ApiOperation(value = "获取扣款记录"
            , notes = "获取扣款记录")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String getDeductionRecord(@ApiParam(value = "sessionThirdKey", required = true)
                                     @RequestParam(value = "sessionThirdKey") String sessionThirdKey
            , @ApiParam(value = "订单号") @RequestParam(value = "tradeNo", required = false, defaultValue = "") String tradeNo
    ) {
        List<WxDeductionRecord> wxDeductionRecordList = wxDeductionRecordService.getDeductionRecordList(sessionThirdKey, tradeNo);
        return ResultUtil.success(wxDeductionRecordList);
    }
}
