package com.lveqia.cloud.zuul.config;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class RedisCacheManager {
    private final static boolean enable = true;
    private final StringRedisTemplate stringRedisTemplate;
    private final Map<String, String> map = new HashMap<>();

    public RedisCacheManager(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public String getToken(String key) {
        return  enable ? stringRedisTemplate.boundValueOps(key).get(): map.get(key);
    }

    public void setToken(String key, String token) {
        if(enable) {
            stringRedisTemplate.boundValueOps(key).set(token);
        }else{
            map.put(key, token);
        }
    }

    public void expireAt(String key, Date date) {
        if(enable) stringRedisTemplate.expireAt(key, date);
    }

    public void delete(String key) {
        if (enable) {
            stringRedisTemplate.delete(key);
        } else {
            map.remove(key);
        }
    }
}
