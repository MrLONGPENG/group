package com.lveqia.cloud.eureka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableEurekaServer
@SpringBootApplication
public class LveqiaCloudEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LveqiaCloudEurekaApplication.class, args);
    }

    @EnableWebSecurity
    static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        private final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
        @Override
        public void configure(WebSecurity web) throws Exception {
            logger.info("WebSecurityConfig-web");
            super.configure(web);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            logger.info("WebSecurityConfig-http");
            http.csrf().ignoringAntMatchers("/eureka/**");  // /eureka/**" 忽略跨域访问
            super.configure(http);
        }
    }
}
