package com.lveqia.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@EnableConfigServer
@EnableEurekaClient
@SpringBootApplication
public class LveqiaCloudConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(LveqiaCloudConfigApplication.class, args);
    }

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }
}
