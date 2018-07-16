package com.mujugroup.wx.controller;

import com.lveqia.cloud.common.IpUtil;
import com.lveqia.cloud.common.ResultUtil;
import com.mujugroup.wx.service.PayApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/pay")
public class PayApiController {
    private final Logger logger = LoggerFactory.getLogger(PayApiController.class);
    private final PayApiService payApiService;

    @Autowired
    public PayApiController(PayApiService payApiService) {
        this.payApiService = payApiService;
    }


    @RequestMapping(value = "/unifiedOrder")
    public String requestPay(HttpServletRequest request, String sessionThirdKey
            , String did, String code, String goods){
        logger.info("wx-requestPay");
        Map<String, String> map =  payApiService.requestPay(sessionThirdKey,did, code, goods, IpUtil.getIpAddr(request));
        if(map==null) return ResultUtil.error(ResultUtil.CODE_UNKNOWN_ERROR);
        if(map.containsKey("code")) return ResultUtil.error(ResultUtil.CODE_VALIDATION_FAIL, map.get("info"));
        return ResultUtil.success(map);
    }

    @RequestMapping("notify")
    public String notify(@RequestBody String notifyXml)  {
        try {
            return payApiService.completePay(notifyXml);
        } catch (Exception e) {
           logger.warn("微信支付回调接收失败",e);
        }
        return ResultUtil.success();
    }
}
