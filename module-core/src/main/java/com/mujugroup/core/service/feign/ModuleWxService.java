package com.mujugroup.core.service.feign;


import com.mujugroup.core.service.feign.error.ModuleWxServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Component(value ="moduleWxService")
@FeignClient(value = "module-wx", fallback = ModuleWxServiceError.class)
public interface ModuleWxService {

    @RequestMapping(value = "/order/getPayCount", method = RequestMethod.POST)
    Map<String, String> getPayCount(@RequestParam(value = "key") String key);

    @RequestMapping(value = "/order/getPaymentInfo", method = RequestMethod.POST)
    Map<String, String> getPaymentInfo(@RequestParam(value = "key") String key);

    @RequestMapping(value = "/uptime/query", method = RequestMethod.POST)
    String queryUptime(@RequestParam(name="key") int key, @RequestParam(name="kid") int kid);
}
