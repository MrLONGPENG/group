package com.mujugroup.lock.config;

import com.lveqia.cloud.common.config.Swagger2Support;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class LockSwagger2 extends Swagger2Support {

    public LockSwagger2() {
        super("Module-Lock", "com.mujugroup.lock.controller");
    }

}
