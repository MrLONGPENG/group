package com.mujugroup.core;

import com.github.wxiaoqi.merge.EnableAceMerge;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableHystrix
@EnableAceMerge
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ModuleCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleCoreApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
