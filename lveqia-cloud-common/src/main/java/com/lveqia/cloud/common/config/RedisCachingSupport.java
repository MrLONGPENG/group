package com.lveqia.cloud.common.config;

import com.google.gson.Gson;
import io.micrometer.core.lang.NonNull;
import io.micrometer.core.lang.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.ClassUtils;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import java.util.Locale;


public abstract class RedisCachingSupport extends CachingConfigurerSupport {

    private String KEY_PREFIX;
    private final static char SEPARATOR = '-';
    private final static int NO_PARAM_KEY = 0;

    private final Logger logger = LoggerFactory.getLogger(RedisCachingSupport.class);

    public RedisCachingSupport(String KEY_PREFIX) {
        this.KEY_PREFIX = KEY_PREFIX;
    }
    /**
     *  注解@Cacheable key生成规则
     */
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append(KEY_PREFIX).append(SEPARATOR);

            strBuilder.append(target.getClass().getSimpleName());
            strBuilder.append(SEPARATOR);

            strBuilder.append(method.getName());
            if (params.length > 0) {
                for (Object object : params) {
                    strBuilder.append(SEPARATOR);
                    if (isSimpleValueType(object.getClass())) {
                        strBuilder.append(object);
                    } else {
                        strBuilder.append( new Gson().toJson(object).hashCode());
                    }
                }
            } else {
                strBuilder.append(SEPARATOR);
                strBuilder.append(NO_PARAM_KEY);
            }
            return strBuilder.toString();
        };
    }

    /**
     * 判断是否是简单值类型.包括：基础数据类型、CharSequence、Number、Date、URL、URI、Locale、Class;
     */
    private static boolean isSimpleValueType(Class<?> clazz) {
        return (ClassUtils.isPrimitiveOrWrapper(clazz) || clazz.isEnum() || CharSequence.class.isAssignableFrom(clazz)
                || Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || URI.class == clazz
                || URL.class == clazz || Locale.class == clazz || Class.class == clazz);
    }

    /**
     * redis数据操作异常处理
     * 这里的处理：在日志中打印出错误信息，但是放行
     * 保证redis服务器出现连接等问题的时候不影响程序的正常运行，使得能够出问题时不用缓存
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(@NonNull RuntimeException e, @NonNull Cache cache, @NonNull Object key) {
                logger.error("redis异常：key=[{}]",key,e);
            }

            @Override
            public void handleCachePutError(@NonNull RuntimeException e, @NonNull Cache cache,  @NonNull Object key
                    , @Nullable Object value) {
                logger.error("redis异常：key=[{}]",key,e);
            }

            @Override
            public void handleCacheEvictError(@NonNull RuntimeException e, @NonNull Cache cache, @NonNull Object key){
                logger.error("redis异常：key=[{}]",key,e);
            }

            @Override
            public void handleCacheClearError(@NonNull RuntimeException e, @NonNull Cache cache) {
                logger.error("redis异常：",e);
            }
        };
    }
}
