package com.mujugroup.data.service.feign;


import com.mujugroup.data.service.feign.error.ModuleAuthServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component(value ="moduleAuthService")
@FeignClient(value = "lveqia-cloud-zuul" ,fallback = ModuleAuthServiceError.class)
public interface ModuleAuthService {

    @RequestMapping(value = "/sys/auth/getUserId",method = RequestMethod.POST)
    int getUserId(@RequestHeader("sessionId") String sessionId);

}
