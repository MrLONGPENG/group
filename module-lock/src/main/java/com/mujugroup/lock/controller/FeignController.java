package com.mujugroup.lock.controller;

import com.lveqia.cloud.common.objeck.to.LockTo;
import com.mujugroup.lock.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RequestMapping(value = "/getLockInfo", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LockTo getLockInfo(@RequestParam(value = "bid") String bid) {
        return feignService.getLockInfo(bid);
    }
    @RequestMapping(value = "/getFailNameByDid", method = RequestMethod.POST)
    public List<String> getFailNameByDid(@RequestParam(value = "did") String did) {
        return feignService.getFailNameByDid(did);
    }
}
