package com.mujugroup.lock;

import com.github.wxiaoqi.merge.EnableAceMerge;
import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.util.DateUtil;
import com.lveqia.cloud.common.util.StringUtil;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;

@EnableAsync
@EnableHystrix
@EnableSwagger2
@EnableAceMerge
@EnableScheduling
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableTransactionManagement
//@EnableAutoConfiguration(exclude={HibernateJpaAutoConfiguration.class})
public class ModuleLockApplication {

    private final Logger logger = LoggerFactory.getLogger(ModuleLockApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ModuleLockApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public MapperFactory getFactory() {
        DefaultMapperFactory defaultMapperFactory = new DefaultMapperFactory.Builder().build();
        // 订单类型转换
        defaultMapperFactory.getConverterFactory().registerConverter("orderTypeConvert"
                , new BidirectionalConverter<Integer, String>() {
                    @Override
                    public String convertTo(Integer source, Type<String> destinationType) {
                        return source == 1 ? "晚休" : source == 2 ? "午休" : "未知";
                    }

                    @Override
                    public Integer convertFrom(String source, Type<Integer> destinationType) {
                        return "晚修".equals(source) ? 1 : "午休".equals(source) ? 2 : 0;
                    }
                });
        //百分数转换
        defaultMapperFactory.getConverterFactory().registerConverter("getPercentConvert"
                , new BidirectionalConverter<Integer, String>() {


                    @Override
                    public String convertTo(Integer source, Type<String> destinationType) {
                        return StringUtil.Percent(source.doubleValue(), 100.0);
                    }

                    @Override
                    public Integer convertFrom(String source, Type<Integer> destinationType) {
                        return null;
                    }
                });
        //充电状态转换
        defaultMapperFactory.getConverterFactory().registerConverter("electricConvert"
                , new BidirectionalConverter<Integer, String>() {
                    @Override
                    public String convertTo(Integer source, Type<String> destinationType) {
                        return source == 0 ? "未充电" : "充电中";
                    }

                    @Override
                    public Integer convertFrom(String source, Type<Integer> destinationType) {
                        if ("未充电".equals(source)) {
                            return 0;
                        } else if ("充电中".equals(source)) {
                            return 1;
                        } else {
                            return null;
                        }
                    }
                });
        // 开关锁状态转换
        defaultMapperFactory.getConverterFactory().registerConverter("statusTypeConvert"
                , new BidirectionalConverter<Integer, String>() {
                    @Override
                    public String convertTo(Integer source, Type<String> destinationType) {
                        return source == 1 ? "关锁" : source == 2 ? "开锁" : "未知";
                    }

                    @Override
                    public Integer convertFrom(String source, Type<Integer> destinationType) {
                        return "关锁".equals(source) ? 1 : "开锁".equals(source) ? 2 : 0;
                    }
                });

        defaultMapperFactory.getConverterFactory().registerConverter("resolveTypeConvert"
                , new BidirectionalConverter<Integer, String>() {
                    @Override
                    public String convertTo(Integer source, Type<String> destinationType) {
                        return source == 1 ? "产生异常" : source == 2 ? "解决中" : source == 4 ? "已解决" : "未解决";
                    }

                    @Override
                    public Integer convertFrom(String source, Type<Integer> destinationType) {
                        return null;
                    }
                });
        // 日期转换
        defaultMapperFactory.getConverterFactory().registerConverter("dateConvert"
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
        defaultMapperFactory.getConverterFactory().registerConverter("dateConvertStr"
                , new BidirectionalConverter<Date, String>() {


                    @Override
                    public String convertTo(Date source, Type<String> destinationType) {
                        return DateUtil.dateToString(source, DateUtil.TYPE_CHINESE_FORMAT);
                    }

                    @Override
                    public Date convertFrom(String source, Type<Date> destinationType) {
                        return null;
                    }
                });
        return defaultMapperFactory;

    }


    /**
     * 自定义异步线程池
     */
    @Bean
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        poolTaskExecutor.setThreadNamePrefix("Lock-Executor");
        poolTaskExecutor.setMaxPoolSize(10);
        poolTaskExecutor.setCorePoolSize(10);
        poolTaskExecutor.setQueueCapacity(1000);
        poolTaskExecutor.setAllowCoreThreadTimeOut(true);
        poolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        poolTaskExecutor.setRejectedExecutionHandler((r, e) -> logger.error("Lock Task size > 1000, Does nothing"));
        // 使用预定义的异常处理类 - 使用该策略时，如果线程池队列满了丢掉这个任务并且抛出
        // poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        // 使用预定义的异常处理类 - 如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常
        // poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        // 使用预定义的异常处理类 - 如果添加到线程池失败，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行
        // poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return poolTaskExecutor;
    }
}
