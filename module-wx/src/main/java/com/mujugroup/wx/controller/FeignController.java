package com.mujugroup.wx.controller;

import com.lveqia.cloud.common.objeck.to.*;
import com.mujugroup.wx.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 服务模块之间调用接口
 */
@RestController
@RequestMapping("/feign")
public class FeignController {

    private final FeignService feignService;

    @Autowired
    public FeignController(FeignService feignService) {
        this.feignService = feignService;
    }

    @ResponseBody
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PageTo<OrderTo> getOrderList(@RequestBody RequestTo requestTo) {
        return feignService.getOrderList(requestTo);
    }

    @RequestMapping(value = "/getCountByUsingDid", method = RequestMethod.GET)
    public int getCountByUsingDid(String did, long time) {
        return feignService.getCountByUsingDid(did, time);
    }

    @ResponseBody
    @RequestMapping(value = "/getPayInfoByDid", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public PayInfoTo getPayInfoByDid(@RequestParam(value = "did") String did) {
        return feignService.getPayInfoByDid(did);
    }

    @ResponseBody
    @RequestMapping(value = "/getUptimeTo", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UptimeTo getUptimeTo(@RequestParam(name = "aid") int aid , @RequestParam(name = "hid") int hid
            , @RequestParam(name = "oid") int oid) {
        return  feignService.getUptimeTo(aid, hid, oid);

    }

}
