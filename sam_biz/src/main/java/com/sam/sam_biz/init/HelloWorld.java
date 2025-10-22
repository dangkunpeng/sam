package com.sam.sam_biz.init;

import com.sam.sap_commons.utils.RedisCacheHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Order(999)
@Slf4j
@Component
public class HelloWorld implements ApplicationRunner {
    @Value("${sam.helloworld}")
    private String helloworld;

    @Resource
    private CacheDemo cacheDemo;
    @Resource
    private RedisCacheHelper redisCacheHelper;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("================ {} =================", helloworld);
        redisCacheHelper.set("helloworld", helloworld, 60L, TimeUnit.MINUTES);
        log.info("CacheDemo={}",cacheDemo.getCacheKey("testkey"));
        log.info("CacheDemo={}",cacheDemo.getCacheKey("testkey"));
    }
}
