package com.sam.sam_biz.init;

import com.sam.sam_biz.customer.SamAppConfig;
import com.sam.sam_biz.demo.CacheDemo;
import com.sam.sap_commons.utils.JsonUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(999)
@Slf4j
@Component
public class HelloWorldInit implements ApplicationRunner {
    @Value("${sam.helloworld}")
    private String helloworld;

    @Autowired
    private SamAppConfig samAppConfig;

    @Resource
    private CacheDemo cacheDemo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("================ {} =================", helloworld);
        log.info("samAppConfig={}", JsonUtil.toJsonString(samAppConfig));
//        log.info("CacheKey={}",cacheDemo.getCacheKey("helloworld"));
//        log.info("CacheKey={}",cacheDemo.getCacheKey("helloworld"));
//        log.info("DemoKey={}",cacheDemo.getDemoKey("helloworld"));
//        log.info("DemoKey={}",cacheDemo.getDemoKey("helloworld"));
//        log.info("CacheKey={}",cacheDemo.getCacheKey("helloworld"));
//        log.info("DemoKey={}",cacheDemo.getDemoKey("helloworld"));
    }
}
