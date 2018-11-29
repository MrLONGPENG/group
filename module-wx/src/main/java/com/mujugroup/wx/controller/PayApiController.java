package com.mujugroup.wx.controller;

import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.util.IpUtil;
import com.lveqia.cloud.common.util.ResultUtil;
import com.mujugroup.wx.config.MyConfig;
import com.mujugroup.wx.service.PayApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/pay")
@Api(description="微信小程序支付接口")
public class PayApiController {
    private final Logger logger = LoggerFactory.getLogger(PayApiController.class);
    private final PayApiService payApiService;

    @Autowired
    public PayApiController(PayApiService payApiService) {
        this.payApiService = payApiService;
    }


    @ApiOperation(value="统一下单接口", notes="支持小程序下单")
    @RequestMapping(value = "/unifiedOrder", method = RequestMethod.POST)
    public String requestPay(HttpServletRequest request, String sessionThirdKey
            , String did, String code, String goods) throws BaseException {
        return ResultUtil.success(payApiService.requestPay(sessionThirdKey, code, goods, IpUtil.getIpAddr(request)));
    }

    // 小程序支付完成回调接口
    @RequestMapping(value = "notify",  method = RequestMethod.POST)
    public String notify(@RequestBody String notifyXml)  {
        try {
            return payApiService.completePay(notifyXml);
        } catch (Exception e) {
           logger.warn("微信支付回调接收失败",e);
        }
        return ResultUtil.success();
    }

    /**
     * 申请退款
     *
     * @param index        订单退款序号
     * @param orderNo      商户订单号， 退款单号=订单号+退款序号
     * @param totalFee      订单金额
     * @param refundFee     退款金额
     */
    @RequestMapping(value = "refund",  method = RequestMethod.POST)
    public String refund(int index, String orderNo, Long totalFee,Long refundFee, String refundDesc) {
        Map<String, String> map = payApiService.refund(index, orderNo, totalFee,refundFee
                , refundDesc, MyConfig.REFUND_SOURCE_UNSETTLED_FUNDS);
        if(map==null) return ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR);
        return ResultUtil.success(map);

    }

}
