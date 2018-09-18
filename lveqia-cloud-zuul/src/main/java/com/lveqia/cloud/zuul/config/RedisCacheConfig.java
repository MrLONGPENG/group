package com.lveqia.cloud.zuul.config;

import com.lveqia.cloud.common.config.RedisCachingSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class RedisCacheConfig extends RedisCachingSupport {

    public RedisCacheConfig() {
        super("auth");
    }


}
