package com.mujugroup.lock.service.feign;


import com.lveqia.cloud.common.objeck.to.PayInfoTo;
import com.lveqia.cloud.common.objeck.to.UptimeTo;
import com.mujugroup.lock.service.feign.error.ModuleWxServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Component(value = "moduleWxService")
@FeignClient(value = "module-wx", fallback = ModuleWxServiceError.class)
public interface ModuleWxService {

    @RequestMapping(value = "/feign/usingNotify", method = RequestMethod.POST)
    String usingNotify(@RequestParam(value = "did")long did, @RequestParam(value = "lockStatus")int lockStatus);

    @RequestMapping(value = "/merge/getOrderEndTimeByDid", method = RequestMethod.POST)
    Map<String, String> getOrderEndTimeByDid(@RequestParam(value = "param") String param);

    @RequestMapping(value = "/feign/getPayInfoByDid", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PayInfoTo getPayInfoByDid(@RequestParam(value = "did") String did,@RequestParam(value = "orderType") int orderType);

    @RequestMapping(value = "/feign/getUptimeTo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    UptimeTo getUptimeTo(@RequestParam(name = "aid") String aid, @RequestParam(name = "hid") String hid
            , @RequestParam(name = "oid") String oid);
}
