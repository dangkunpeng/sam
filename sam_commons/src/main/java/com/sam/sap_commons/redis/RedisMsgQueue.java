package com.sam.sap_commons.redis;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisMsgQueue {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * leftPush 和 rightPop 可以实现一个简单的队列
     *
     * @param key
     * @param value
     */
    public void leftPush(String key, String value) {
        stringRedisTemplate.opsForList().leftPush(key, value);
    }

    public Object rightPop(String key) {
        return stringRedisTemplate.opsForList().rightPop(key);
    }

}
