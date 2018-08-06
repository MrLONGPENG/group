package com.lveqia.cloud.common.cache;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 抽象Guava缓存类、缓存模板。
 * 子类需要实现fetchData(key)，从数据库或其他数据源（如Redis）中获取数据。
 * 子类调用getValue(key)方法，从缓存中获取数据，并处理不同的异常，比如value为null时的InvalidCacheLoadException异常。
 *
 * @author leolaurel
 *
 * @param <K> key 类型
 * @param <V> value 类型
  */
public abstract class GuavaCache <K, V>{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ListeningExecutorService backgroundRefreshPools;
    private LoadingCache<K, V> caches;

    //用于初始化cache的参数及其缺省值
    private int threadSize = 10;                    //线存池大小
    private int maximumSize = 1000;					//最大缓存条数
    private int expireAfterWriteDuration = 60;		//数据存在时长
    private int refreshAfterWriteDuration = 30;		//数据刷新时长，正常情况下比存在时间短
    private TimeUnit timeUnit = TimeUnit.MINUTES;	//时间单位（分钟）

    /**
     * 通过调用getCache().get(key)来获取数据
     * @return cache
     */
    public LoadingCache<K, V> getCache() {
        if(caches == null){	//使用双重校验锁保证只有一个cache实例
            synchronized (this) {
                if(caches == null){
                    this.backgroundRefreshPools = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(threadSize));
                    this.caches = CacheBuilder.newBuilder().maximumSize(maximumSize)//缓存数据的最大条目
                            .expireAfterWrite(expireAfterWriteDuration, timeUnit)	//数据被创建多久后被移除
                            .refreshAfterWrite(refreshAfterWriteDuration, timeUnit) //数据每隔多久异步刷新缓存
                            .recordStats()											//启用统计
                            .build(new CacheLoader<K, V>() {
                                @Override
                                public V load(K key) throws Exception {
                                    logger.debug("首次或重新加载缓存: " + key);
                                    return loadData(key);
                                }
                                @Override
                                public ListenableFuture<V> reload(K key, V oldValue){
                                    return backgroundRefreshPools.submit(() -> {
                                        logger.debug("异步刷新缓存: " + key);
                                        return loadData(key);
                                    });
                                }
                            });
                    logger.debug("本地缓存{}初始化成功", this.getClass().getSimpleName());
                }
            }
        }

        return caches;
    }

    /**
     * 加载数据或达到时间重新异步加载数据
     * @param key 缓存保存名字
     * @return value 缓存内容
     */
    protected abstract V loadData(K key);


    /**
     * 从缓存中获取数据（第一次自动调用loadData获取数据），并处理异常
     * @param key 缓存名字
     * @return Value
     * @throws ExecutionException
     */
    protected V getValue(K key) throws ExecutionException {
        return getCache().get(key);
    }


    public void setThreadSize(int threadSize) {
        this.threadSize = threadSize;
    }

    public void setMaximumSize(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    public void setExpireAfterWriteDuration(int expireAfterWriteDuration) {
        this.expireAfterWriteDuration = expireAfterWriteDuration;
    }

    public void setRefreshAfterWriteDuration(int refreshAfterWriteDuration) {
        this.refreshAfterWriteDuration = refreshAfterWriteDuration;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
