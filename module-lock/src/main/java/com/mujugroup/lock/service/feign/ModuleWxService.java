package com.mujugroup.lock.service.feign;


import com.mujugroup.lock.service.feign.error.ModuleWxServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Component(value = "moduleWxService")
@FeignClient(value = "module-wx", fallback = ModuleWxServiceError.class)
public interface ModuleWxService {

    @RequestMapping(value = "/using/notify", method = RequestMethod.POST)
    String usingNotify(@RequestParam(value = "bid") String bid, @RequestParam(value = "lockStatus") String lockStatus);

    @RequestMapping(value = "/merge/getOrderEndTimeByDid",method = RequestMethod.POST)
    Map<String, String> getOrderEndTimeByDid(@RequestParam(value = "param") String param);
}
