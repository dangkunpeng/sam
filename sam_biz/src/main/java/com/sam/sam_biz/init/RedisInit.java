package com.sam.sam_biz.init;

import com.sam.sap_commons.redis.RedisClient;
import com.sam.sap_commons.redis.RedisKeyTool;
import com.sam.sap_commons.utils.KeyTool;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisInit implements ApplicationRunner {

    @Resource
    private RedisClient redisClient;

    @Resource
    private RedisKeyTool redisKeyTool;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 100; i++) {
            String key = redisKeyTool.newKey("key");
            String val = redisKeyTool.newKey("val");
            stringRedisTemplate.opsForValue().set(key,val);
            log.info("RedisInit key={}, val={}", key, stringRedisTemplate.opsForValue().get(key));
        }
    }
}
