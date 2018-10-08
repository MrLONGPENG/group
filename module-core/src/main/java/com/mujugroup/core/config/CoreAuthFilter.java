package com.mujugroup.core.config;

import com.lveqia.cloud.common.config.AuthFilter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
@Component
public class CoreAuthFilter extends AuthFilter {
    public CoreAuthFilter() { // 指定放行Url
        super(Collections.unmodifiableSet(new HashSet<>(Arrays.asList("/core", "/agent/list"
                , "/agent/list/{id}","/auth/tree","/device/query","/device/list","/hospital/list","/region/child","/status/info"))));
    }
}
