package com.mujugroup.data.config;

import com.lveqia.cloud.common.config.Swagger2Support;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class DataSwagger2 extends Swagger2Support {

    public DataSwagger2() {
        super("Module-Data", "com.mujugroup.data.controller");
    }
}
