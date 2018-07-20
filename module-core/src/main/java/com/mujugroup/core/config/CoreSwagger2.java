package com.mujugroup.core.config;

import com.lveqia.cloud.common.config.Swagger2Support;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class CoreSwagger2 extends Swagger2Support {

    public CoreSwagger2() {
        super("Module-Core", "com.mujugroup.core.controller");
    }
}
