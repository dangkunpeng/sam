package com.sam.sap_commons.utils;

import com.rabbitmq.tools.json.JSONUtil;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheHelper {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 设置缓存（带过期时间）
    public <T> void set(String key, T value) {
        set(key, value, 12, TimeUnit.HOURS);
    }

    // 设置缓存（带过期时间）
    public <T> void set(String key, T value, long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JsonUtil.toJsonString(value), time, unit);
    }

    // 获取缓存（解决缓存穿透：缓存空值）
    public <T> T get(String key, Class<T> type) {
        String json = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(json)) {
            // 判断是否为缓存的空值（防止缓存穿透）
            // 缓存不存在，返回null，由调用方决定是否从数据源加载
            return null;
        }
        return JsonUtil.toObj(json, type);
    }

    // 删除缓存
    public Boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }

    // 判断缓存是否存在
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }
}