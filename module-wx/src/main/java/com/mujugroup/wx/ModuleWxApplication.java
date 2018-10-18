package com.mujugroup.wx;

import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.StringUtil;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        DefaultMapperFactory defaultMapperFactory = new DefaultMapperFactory.Builder().build();
        defaultMapperFactory.getConverterFactory().registerConverter("rmbPriceConvert"
                , new BidirectionalConverter<Integer,Double>(){
                    @Override
                    public Double convertTo(Integer source, Type<Double> destinationType) {
                        return  BigDecimal.valueOf(source).divide(new BigDecimal(100),2, RoundingMode.UP).doubleValue();
                    }
                    @Override
                    public Integer convertFrom(Double source, Type<Integer> destinationType) {
                        return  new BigDecimal(source).setScale(2, BigDecimal.ROUND_HALF_UP)
                                .multiply(new BigDecimal(100.0)).intValue();
                    }
                });
        return defaultMapperFactory;
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
