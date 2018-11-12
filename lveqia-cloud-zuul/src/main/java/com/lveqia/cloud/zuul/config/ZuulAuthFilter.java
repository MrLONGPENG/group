package com.lveqia.cloud.zuul.config;

import com.lveqia.cloud.common.config.AuthFilter;
import org.springframework.stereotype.Component;

@Component
public class ZuulAuthFilter extends AuthFilter {
    public ZuulAuthFilter() { // 指定放行Url
        super(true);
    }
}
