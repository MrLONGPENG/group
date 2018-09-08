package com.lveqia.cloud.zuul.config;

import com.lveqia.cloud.common.config.Swagger2Support;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class ZuulSwagger2 extends Swagger2Support {

    public ZuulSwagger2() {
        super("Module-Zuul", "com.lveqia.cloud.zuul.controller");
    }
}
