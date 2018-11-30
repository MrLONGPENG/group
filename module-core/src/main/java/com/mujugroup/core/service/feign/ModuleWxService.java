package com.mujugroup.core.service.feign;


import com.lveqia.cloud.common.objeck.to.UptimeTo;
import com.mujugroup.core.service.feign.error.ModuleWxServiceError;
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

    @RequestMapping(value = "/merge/getPayCount", method = RequestMethod.POST)
    Map<String, String> getPayCount(@RequestParam(value = "param") String param);

    @RequestMapping(value = "/merge/getPaymentInfo", method = RequestMethod.POST)
    Map<String, String> getPaymentInfo(@RequestParam(value = "param") String param);

    @RequestMapping(value = "/feign/getUptimeTo", method = RequestMethod.POST
            , produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    UptimeTo getUptimeTo(@RequestParam(name = "aid") int aid, @RequestParam(name = "hid") int hid
            , @RequestParam(name = "oid") int oid);

    @RequestMapping(value = "/feign/getCountByUsingDid", method = RequestMethod.GET)
    int getCountByUsingDid(@RequestParam(value = "did") String did, @RequestParam(value = "time") long time);

    @RequestMapping(value = "/merge/getOrderEndTimeByDid", method = RequestMethod.POST)
    Map<String, String> getOrderEndTimeByDid(@RequestParam(value = "param") String param);
}
