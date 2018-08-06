package com.github.wxiaoqi.merge.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ace
 * @create 2018/2/3.
 */
@Data
@ConfigurationProperties("merge")
public class MergeProperties {
    /**
     * guava缓存的键值数
     */
    private Integer guavaCacheNumMaxSize;

    /**
     * guava缓存过期时间
     */
    private Integer guavaCacheExpireWriteTime;
    /**
     * guava更新混存的下一次时间,分钟
     */
    private Integer guavaCacheRefreshWriteTime;
    /**
     * guava
     */
    private Integer guavaCacheRefreshThreadPoolSize;

    public Integer getGuavaCacheNumMaxSize() {
        return guavaCacheNumMaxSize;
    }

    public void setGuavaCacheNumMaxSize(Integer guavaCacheNumMaxSize) {
        this.guavaCacheNumMaxSize = guavaCacheNumMaxSize;
    }

    public Integer getGuavaCacheExpireWriteTime() {
        return guavaCacheExpireWriteTime;
    }

    public void setGuavaCacheExpireWriteTime(Integer guavaCacheExpireWriteTime) {
        this.guavaCacheExpireWriteTime = guavaCacheExpireWriteTime;
    }

    public Integer getGuavaCacheRefreshWriteTime() {
        return guavaCacheRefreshWriteTime;
    }

    public void setGuavaCacheRefreshWriteTime(Integer guavaCacheRefreshWriteTime) {
        this.guavaCacheRefreshWriteTime = guavaCacheRefreshWriteTime;
    }

    public Integer getGuavaCacheRefreshThreadPoolSize() {
        return guavaCacheRefreshThreadPoolSize;
    }

    public void setGuavaCacheRefreshThreadPoolSize(Integer guavaCacheRefreshThreadPoolSize) {
        this.guavaCacheRefreshThreadPoolSize = guavaCacheRefreshThreadPoolSize;
    }
}
