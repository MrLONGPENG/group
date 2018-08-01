package com.mujugroup.core.service.feign;


import com.mujugroup.core.service.feign.error.ModuleLockServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Component(value ="moduleLockService")
@FeignClient(value = "module-lock" ,fallback = ModuleLockServiceError.class)
public interface ModuleLockService {

    @RequestMapping(value = "/info/getHardwareInfo", method = RequestMethod.POST)
    Map<String, String> getHardwareInfo(@RequestParam(value = "key") String key);

}
