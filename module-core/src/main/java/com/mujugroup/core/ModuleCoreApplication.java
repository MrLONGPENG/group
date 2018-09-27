package com.mujugroup.core;

import com.gexin.fastjson.JSONArray;
import com.github.wxiaoqi.merge.EnableAceMerge;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.util.StringUtil;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableHystrix
@EnableAceMerge
@EnableScheduling
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class ModuleCoreApplication {

    private Gson gson = new GsonBuilder().create();
    public static void main(String[] args) {
        SpringApplication.run(ModuleCoreApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MapperFactory getFactory(){
        DefaultMapperFactory defaultMapperFactory = new DefaultMapperFactory.Builder().build();
        //时间戳转时间
        defaultMapperFactory.getConverterFactory().registerConverter("strToJsonArray"
                , new BidirectionalConverter<String, JsonArray>(){
                    @Override
                    public JsonArray convertTo(String source, Type<JsonArray> destinationType) {
                        return gson.fromJson(source, JsonArray.class);
                    }
                    @Override
                    public String convertFrom(JsonArray source, Type<String> destinationType) {
                        return gson.toJson(source);
                    }
                });
        return defaultMapperFactory;
    }
}
