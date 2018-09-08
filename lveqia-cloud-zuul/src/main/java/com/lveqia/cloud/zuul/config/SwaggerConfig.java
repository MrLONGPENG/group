package com.lveqia.cloud.zuul.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final ZuulProperties properties;

    private final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);
    @Autowired
    public SwaggerConfig(ZuulProperties properties) {
        this.properties = properties;
    }

    @Bean
    @Primary
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>();
            resources.add(createResource("module-auth","/**"));
            properties.getRoutes().values().forEach(route ->
                    resources.add(createResource(route.getServiceId(), route.getPath())));
            return resources;
        };
    }

    private SwaggerResource createResource(String name, String location) {
        logger.info("name:{} location:{}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setSwaggerVersion("2.0");
        swaggerResource.setLocation(location.replaceAll("\\*\\*","v2/api-docs"));
        return swaggerResource;
    }
}
