package com.mujugroup.lock.config;

import com.lveqia.cloud.common.config.AuthFilter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
public class CoreAuthFilter extends AuthFilter {
    public CoreAuthFilter() { // 指定放行Url
        super(new HashSet<>(Arrays.asList("/v2/api-docs","/receive/data","/did/getDeviceId")));
    }
}
