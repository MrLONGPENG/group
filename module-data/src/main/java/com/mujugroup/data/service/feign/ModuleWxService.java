package com.mujugroup.data.service.feign;


import com.mujugroup.data.service.feign.error.ModuleWxServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Component(value ="moduleWxService")
@FeignClient(value = "module-wx" ,fallback = ModuleWxServiceError.class)
public interface ModuleWxService {

    @RequestMapping(value = "/merge/getUserCount",method = RequestMethod.POST)
    Map<String, String> getUserCount(@RequestParam(value = "param") String param);

    @RequestMapping(value = "/merge/getUsageCount",method = RequestMethod.POST)
    Map<String, String> getUsageCount(@RequestParam(value = "param") String param);



}
