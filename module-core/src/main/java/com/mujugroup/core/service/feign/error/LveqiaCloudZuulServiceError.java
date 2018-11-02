package com.mujugroup.core.service.feign.error;

import com.mujugroup.core.service.feign.LveqiaCloudZuulService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Component
public class LveqiaCloudZuulServiceError implements LveqiaCloudZuulService {

    @Override
    public Map<String, String> getNameByUid(@RequestParam(value = "param") String param) {
        return new HashMap<>();
    }
}
