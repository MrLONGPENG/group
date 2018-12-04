package com.mujugroup.wx.service.feign;


import com.github.pagehelper.PageInfo;
import com.lveqia.cloud.common.objeck.to.DataTo;
import com.mujugroup.wx.service.feign.error.ModuleLockServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component(value = "moduleLockService")
@FeignClient(value = "module-lock", fallback = ModuleLockServiceError.class)
public interface ModuleLockService {


    @RequestMapping(value = "/feign/unlock", method = RequestMethod.POST)
    String unlock(@RequestParam(value = "did") String did);


    @RequestMapping(value = "/feign/getLockStatus", method = RequestMethod.POST)
    String getLockStatus(@RequestParam(value = "did") String did);

    @RequestMapping(value = "/feign/getFailTimeoutRecordList", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<DataTo> getFailTimeoutRecordList(@RequestParam(value = "pageNum") int pageNum, @RequestParam(value = "pageSize") int pageSize);

    @RequestMapping(value = "/feign/getRecordByDidAndLastRefresh", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    DataTo getRecordByDidAndLastRefresh(@RequestParam(value = "did") long did, @RequestParam(value = "lastRefresh") long lastRefresh);

}
