package com.sam.sam_biz.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class AsyncService {

    @Async
    public CompletableFuture<Integer> run() {
        Integer sleeps = ThreadLocalRandom.current().nextInt(300, 3000);
        try {

            log.info("sleeps = {}", sleeps);
            Thread.sleep(sleeps);
        } catch (InterruptedException e) {
            log.info("InterruptedException = {}", e.getMessage());
        }
        return  CompletableFuture.completedFuture(sleeps);
    }
}
