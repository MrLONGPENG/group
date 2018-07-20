package com.mujugroup.wx.config;

import com.lveqia.cloud.common.config.Swagger2Support;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class WxSwagger2 extends Swagger2Support {

    public WxSwagger2() {
        super("Module-wx", "com.mujugroup.wx.controller");
    }

}
