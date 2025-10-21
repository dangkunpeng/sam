package com.sam.sam_biz.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Order(999)
@Slf4j
@Component
public class HelloWorld implements ApplicationRunner {
    @Value("${sam.helloworld}")
    private String helloworld;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("================ {} =================", helloworld);
    }
}
