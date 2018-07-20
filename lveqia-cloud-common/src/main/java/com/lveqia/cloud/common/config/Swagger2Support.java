package com.lveqia.cloud.common.config;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class Swagger2Support {

    private final String moduleName;
    private final String packageName;

    public Swagger2Support(String moduleName, String packageName) {
        this.moduleName = moduleName;
        this.packageName = packageName;
    }

    @Bean
    public Docket createRestApi() {// 创建API基本信息
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage(packageName))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {// 创建API的基本信息，这些信息会在Swagger UI中进行显示
        return new ApiInfoBuilder()
                .title("木巨微服务模块" + moduleName + " REST ful APIs")// API 标题
                .description("采用Spring Cloud集成Swagger2提供的REST ful APIs")// API描述
                .contact(new Contact("leolaurel","http://www.mujugroup.com"
                        ,"leolaurel@foxmail.com"))
                .version("1.0")// 版本号
                .build();
    }
}
