package com.mujugroup.data;

import com.github.wxiaoqi.merge.EnableAceMerge;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.common.config.Constant;
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
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@EnableHystrix
@EnableAceMerge
@EnableScheduling
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ModuleDataApplication {


    public static void main(String[] args) {
        SpringApplication.run(ModuleDataApplication.class, args);
    }

    @Bean
    public MapperFactory getFactory(){
        DefaultMapperFactory defaultMapperFactory = new DefaultMapperFactory.Builder().build();

        //时间戳转时间
        defaultMapperFactory.getConverterFactory().registerConverter("dateConvert"
                , new BidirectionalConverter<Date, String>(){
                    @Override
                    public String convertTo(Date source, Type<String> destinationType) {
                        return DateUtil.dateToString(source, DateUtil.TYPE_CHINESE_FORMAT );
                    }
                    @Override
                    public Date convertFrom(String source, Type<Date> destinationType) {
                        return DateUtil.stringToDate(source, DateUtil.TYPE_CHINESE_FORMAT);
                    }
                });
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
                , new BidirectionalConverter<Object,String>(){
                    @Override
                    public String convertTo(Object source, Type<String> destinationType) {
                        String val = Constant.DIGIT_ZERO;
                        if(source instanceof Integer){
                            val = String.valueOf(source);
                        }else if(source instanceof String){
                            val = (String)source;
                        }
                        return StringUtil.changeF2Y(val);
                    }
                    @Override
                    public Object convertFrom(String source, Type<Object> destinationType) {
                        return StringUtil.changeY2F(source);
                    }
                });
        // 订单类型转换
        defaultMapperFactory.getConverterFactory().registerConverter("orderTypeConvert"
                , new BidirectionalConverter<Integer, String>(){
                    @Override
                    public String convertTo(Integer source, Type<String> destinationType) {
                        return source==1 ? "晚休": source == 2 ? "午休" :"未知";
                    }
                    @Override
                    public Integer convertFrom(String source, Type<Integer> destinationType) {
                        return "晚休".equals(source) ? 1 : "午休".equals(source) ? 2 : 0;
                    }
                });
        // 锁状态转换
        defaultMapperFactory.getConverterFactory().registerConverter("lockStatusConvert"
                , new BidirectionalConverter<Integer, String>(){
                    @Override
                    public String convertTo(Integer source, Type<String> destinationType) {
                        return source==1 ? "关锁": source == 2 ? "开锁" :"未知";
                    }
                    @Override
                    public Integer convertFrom(String source, Type<Integer> destinationType) {
                        return "关锁".equals(source) ? 1 : "开锁".equals(source) ? 2 : 0;
                    }
                });
        // 充电状态转换
        defaultMapperFactory.getConverterFactory().registerConverter("electricConvert"
                , new BidirectionalConverter<Integer, String>(){
                    @Override
                    public String convertTo(Integer source, Type<String> destinationType) {
                        return source> 0 ? "充电中": "未充电";
                    }
                    @Override
                    public Integer convertFrom(String source, Type<Integer> destinationType) {
                        return "未充电".equals(source) ? 0 : 1;
                    }
                });
        defaultMapperFactory.getConverterFactory().registerConverter("dateConvertStr"
                , new BidirectionalConverter<String, String>() {

                    @Override
                    public String convertTo(String source, Type<String> destinationType) {
                        if (Constant.DIGIT_ZERO.equals(source)) return "无订单信息";
                        return DateUtil.timestampToString(Long.parseLong(source), DateUtil.TYPE_CHINESE_FORMAT);
                    }

                    @Override
                    public String convertFrom(String source, Type<String> destinationType) {
                        return null;
                    }
                });
        return defaultMapperFactory;
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
