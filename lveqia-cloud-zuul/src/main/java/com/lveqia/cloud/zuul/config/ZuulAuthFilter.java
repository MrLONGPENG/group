package com.lveqia.cloud.zuul.config;

import com.lveqia.cloud.common.config.AuthFilter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
public class ZuulAuthFilter extends AuthFilter {
    public ZuulAuthFilter() { // 指定放行Url
        super(Collections.unmodifiableSet(new HashSet<>(Arrays.asList("/v2/api-docs","/wx/**", "/core/**"
                , "/data/**", "/lock/**"))));
    }
}
