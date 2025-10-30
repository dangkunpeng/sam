package com.sam.sam_biz.init;

import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.concurrent.CompletableFuture;
@Order(100)
@Slf4j
@Component
public class AsyncCompleteFutureInit implements ApplicationRunner {

    @Resource
    private AsyncService asyncService;

    @Async
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<CompletableFuture<Integer>> completableFuture = Lists.newArrayList();
        StopWatch stopWatch = new StopWatch("Async");
        stopWatch.start("Async method");
        for (int i = 0; i < 10; i++) {
            completableFuture.add(asyncService.run());
        }
        //将这一批CompletableFuture对象，使用allOf操作得到一个CompletableFuture对象
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(completableFuture.toArray(new CompletableFuture[]{}));
        //等待所有结果执行完毕
        allFutures.join();
        Integer result = completableFuture.stream().map(CompletableFuture::join).mapToInt(Integer::intValue).sum();
        stopWatch.stop();
        log.info("all task costs = {} , stopWatch = {}", result, stopWatch.prettyPrint());
    }
}
