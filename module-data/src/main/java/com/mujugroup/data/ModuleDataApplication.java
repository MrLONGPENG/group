package com.mujugroup.data;

import com.github.wxiaoqi.merge.EnableAceMerge;
import com.lveqia.cloud.common.DateUtil;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
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

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        DefaultMapperFactory defaultMapperFactory = new DefaultMapperFactory.Builder().build();
        //时间戳转时间
        defaultMapperFactory.getConverterFactory().registerConverter("timestampConvert"
                , new BidirectionalConverter<Long, String>(){
                    @Override
                    public String convertTo(Long source, Type<String> destinationType) {
                        return DateUtil.timestampToString(source.intValue(), DateUtil.TYPE_DATETIME_19 );
                    }
                    @Override
                    public Long convertFrom(String source, Type<Long> destinationType) {
                        return DateUtil.stringToDate(source, DateUtil.TYPE_DATETIME_19).getTime()/1000L;
                    }
                });
        //价格分转元，带两位小数
        defaultMapperFactory.getConverterFactory().registerConverter("rmbPriceConvert"
                , new BidirectionalConverter<Object,Double>(){
                    @Override
                    public Double convertTo(Object source, Type<Double> destinationType) {
                        float val = 0;
                        if(source instanceof Integer){
                            val = (int)source/100f;
                        }else if(source instanceof String){
                            val = Integer.parseInt((String)source)/100f;
                        }
                        return new BigDecimal(val).setScale(2, RoundingMode.UP).doubleValue();
                    }
                    @Override
                    public Object convertFrom(Double source, Type<Object> destinationType) {
                        return new Double(source*100).intValue();
                    }
                });

        return defaultMapperFactory;
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
