package com.sam.sam_biz.init;

import com.sam.sap_commons.redis.RedisCacheHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(99)
@Slf4j
@Component
public class RedisInit implements ApplicationRunner {


    @Resource
    private RedisCacheHelper redisCacheHelper;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("================ {} =================", "helloworld");
        for (int i = 0; i < 10; i++) {
            String key = redisCacheHelper.newKey("key");
            String val = redisCacheHelper.newKey("val");
            log.info("RedisInit key={}, val={}", key, val);
        }
        String key = redisCacheHelper.newKey("mqKey");
        for (int i = 0; i < 10; i++) {

            String val = redisCacheHelper.newKey("mqVal");
            redisCacheHelper.leftPush(key, val);
            log.info("mq key={}, val={}", key, redisCacheHelper.rightPop(key));
        }
    }

}
