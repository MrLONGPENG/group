package com.lveqia.cloud.zuul;

import com.lveqia.cloud.zuul.config.filter.SendErrorFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class LveqiaCloudZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(LveqiaCloudZuulApplication.class, args);
    }

    @Bean
    public SendErrorFilter errorFilter(){
        return new SendErrorFilter();
    }

}
