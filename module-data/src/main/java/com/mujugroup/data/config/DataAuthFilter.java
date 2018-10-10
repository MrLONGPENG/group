package com.mujugroup.data.config;

import com.lveqia.cloud.common.config.AuthFilter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;


@Component
public class DataAuthFilter extends AuthFilter {

    public DataAuthFilter() { // 指定放行Url
        super(Collections.unmodifiableSet(new HashSet<>(Arrays.asList("/v2/api-docs", "/statistics/excel"))));
    }
}
