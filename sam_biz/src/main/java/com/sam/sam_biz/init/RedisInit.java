package com.sam.sam_biz.init;

import com.sam.sap_commons.redis.RedisCacheHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(99)
@Slf4j
@Component
public class RedisInit implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("================ {} =================", "helloworld");
        for (int i = 0; i < 10; i++) {
            String key = RedisCacheHelper.newKey("key");
            String val = RedisCacheHelper.newKey("val");
            RedisCacheHelper.leftPush(key, val);
        }
    }

}
