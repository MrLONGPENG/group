package com.mujugroup.data;

import com.github.wxiaoqi.merge.EnableAceMerge;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.client.RestTemplate;

@EnableHystrix
@EnableAceMerge
@EnableScheduling
@EnableFeignClients
@EnableEurekaClient
@EnableRedisHttpSession
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ModuleDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleDataApplication.class, args);
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
