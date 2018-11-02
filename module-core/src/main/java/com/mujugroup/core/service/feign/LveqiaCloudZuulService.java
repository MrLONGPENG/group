package com.mujugroup.core.service.feign;

import com.mujugroup.core.service.feign.error.LveqiaCloudZuulServiceError;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Component(value = "lveqiaCloudZuulService")
@FeignClient(value = "lveqia-cloud-zuul", fallback = LveqiaCloudZuulServiceError.class)
public interface LveqiaCloudZuulService {

    @RequestMapping(value = "/merge/getNameByUid", method = RequestMethod.POST)
    Map<String, String> getNameByUid(@RequestParam(value = "param") String param);
}
