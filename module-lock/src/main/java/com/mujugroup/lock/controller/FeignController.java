package com.mujugroup.lock.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.objeck.to.DataTo;
import com.lveqia.cloud.common.objeck.to.LockTo;
import com.mujugroup.lock.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    public String unlock(@RequestParam(value = "did") String did) {
        return feignService.unlock(did);
    }

    @RequestMapping(value = "/beep", method = RequestMethod.POST)
    public String beep(@RequestParam(value = "did") String did) {
        return feignService.beep(did);
    }

    @ResponseBody
    @RequestMapping(value = "/getLockInfo", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public LockTo getLockInfo(@RequestParam(value = "did") String did) {
        return feignService.getLockInfo(did);
    }


    @RequestMapping(value = "/getLockStatus", method = RequestMethod.POST)
    public String getLockStatus(@RequestParam(value = "did") String did) {
        return feignService.getLockStatus(did);
    }

    @RequestMapping(value = "/getFailNameByDid", method = RequestMethod.POST)
    public List<String> getFailNameByDid(@RequestParam(value = "did") String did) {
        return feignService.getFailNameByDid(did);
    }


    @RequestMapping(value = "/getFailTimeoutRecordList", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<DataTo> getFailTimeoutRecordList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum
            , @RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize) {
        PageHelper.startPage(pageNum, pageSize, false);
        return feignService.getFailRecordList();
    }

    @RequestMapping(value = "/getRecordByDidAndLastRefresh", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DataTo getRecordByDidAndLastRefresh(@RequestParam(value = "did") long did, @RequestParam(value = "lastRefresh") long lastRefresh) {
        return feignService.getRecordByDidAndLastRefresh(did, lastRefresh);
    }
}
