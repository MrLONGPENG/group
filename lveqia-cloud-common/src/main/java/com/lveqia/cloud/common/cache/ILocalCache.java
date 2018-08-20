package com.lveqia.cloud.common.cache;


/**
 * 本地缓存接口 (在分布式项目中使用本地缓存请慎重)
 * @author leolaurel
 *
 * @param <K> Key的类型
 * @param <V> Value的类型
 */
public interface ILocalCache <K, V> {

    /**
     * 从缓存中获取数据
     * @param key 缓存名
     * @return value
     */
    V get(K key);

}
