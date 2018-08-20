package com.mujugroup.data.service.feign;


import com.mujugroup.data.service.feign.error.ModuleCoreServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Component(value ="moduleCoreService")
@FeignClient(value = "module-core" ,fallback = ModuleCoreServiceError.class)
public interface ModuleCoreService {

    @RequestMapping(value = "/merge/getActiveValue",method = RequestMethod.POST)
    Map<String, String> getActiveValue(@RequestParam(value = "param") String param);

    @RequestMapping(value = "/merge/getTotalActiveCount",method = RequestMethod.POST)
    Map<String, String> getTotalActiveCount(@RequestParam(value = "param") String param);
}
