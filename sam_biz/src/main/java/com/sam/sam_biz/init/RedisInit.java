package com.sam.sam_biz.init;

import com.sam.sap_commons.redis.RedisClient;
import com.sam.sap_commons.redis.RedisKeyTool;
import com.sam.sap_commons.utils.KeyTool;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisInit implements ApplicationRunner {

    @Resource
    private RedisClient redisClient;

    @Resource
    private RedisKeyTool redisKeyTool;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisTemplate.keys("*").forEach(key -> {
            redisClient.delete(key);
            log.info("RedisInit deleted key={}", key);
        });
        for (int i = 0; i < 10000; i++) {
            String key = redisKeyTool.newKey("key");
            String val = redisKeyTool.newKey("val");
            redisClient.set(key, val);
            log.info("RedisInit set key={}, val={}", key, redisClient.get(key));
        }
//        for (int i = 0; i < 10; i++) {
//            String key = KeyTool.newKey("key") + i % 5;
//            redisClient.set(key, KeyTool.newKey("val"));
//            log.info("RedisInit set key={}, val={}", key, redisClient.get(key));
//            log.info(KeyTool.newKey("val"));
//        }
    }
}
