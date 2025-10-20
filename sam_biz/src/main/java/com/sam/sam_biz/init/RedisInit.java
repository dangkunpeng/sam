package com.sam.sam_biz.init;

import com.sam.sam_redis.utils.RedisClient;
import com.sam.sap_commons.utils.KeyTool;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisInit implements ApplicationRunner {

    @Resource
    private RedisClient redisClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 100; i++) {
            String key = KeyTool.newKey("redisInitTestKey") + i % 5;
            redisClient.set(key, KeyTool.newKey("val"));
            log.info("RedisInit set key={}, val={}", key, redisClient.get(key));
        }
    }
}
