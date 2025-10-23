package com.sam.sam_biz.init;

import com.sam.sap_commons.redis.RedisKeyTool;
import com.sam.sap_commons.redis.RedisMsgQueue;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Order(99)
@Slf4j
@Component
public class RedisInit implements ApplicationRunner {


    @Resource
    private RedisKeyTool redisKeyTool;
    @Resource
    private RedisMsgQueue redisMsgQueue;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("================ {} =================", "helloworld");
        for (int i = 0; i < 10; i++) {
            String key = redisKeyTool.newKey("key");
            String val = redisKeyTool.newKey("val");
//            stringRedisTemplate.opsForValue().set(key, val);
            log.info("RedisInit key={}, val={}", key, val);
        }
        String key = redisKeyTool.newKey("mqKey");
        for (int i = 0; i < 10; i++) {

            String val = redisKeyTool.newKey("mqVal");
            redisMsgQueue.leftPush(key, val);
            log.info("mq key={}, val={}", key, redisMsgQueue.rightPop(key));
        }
    }

}
