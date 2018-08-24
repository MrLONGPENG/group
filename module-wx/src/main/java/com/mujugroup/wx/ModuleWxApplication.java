package com.mujugroup.wx;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableCaching
@EnableHystrix
@EnableScheduling
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ModuleWxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleWxApplication.class, args);
    }

    @Bean
    public MapperFactory getFactory(){
        return new DefaultMapperFactory.Builder().build();
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
