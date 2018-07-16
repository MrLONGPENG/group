package com.lveqia.cloud.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class LveqiaCloudEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(LveqiaCloudEurekaApplication.class, args);
    }
}
