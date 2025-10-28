package com.sam.sam_biz.init;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.sam.sam_biz.common.bean.DictBean;
import com.sam.sam_biz.common.service.DictService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InitDict implements ApplicationRunner {

    @Resource
    private DictService dictService;
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 1000,
            300, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2000));

    @Order(1)
    @Override
    @Async
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<CompletableFuture<List<DictBean>>> futures = Lists.newArrayList("1","2")
                .stream()
                .map(dictType -> CompletableFuture.supplyAsync(() -> init("helloWorld"), executor))
                .toList();
        //将这一批CompletableFuture对象，使用allOf操作得到一个CompletableFuture对象
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}));
        //等待所有结果执行完毕
        allFutures.join();
        //拿到所有结果（注意此处逻辑是主线程去进行的map操作，可进行优化）
        List<List<DictBean>> typeList = futures.stream().map(CompletableFuture::join).toList();
        List<DictBean> itemList = Lists.newArrayList();
        for (List<DictBean> items : typeList) {
            itemList.addAll(items);
        }
        log.info("cached {} dictItem for {} dictType in {} seconds",itemList.size(),typeList.size(), stopwatch.elapsed(TimeUnit.SECONDS));
        executor.shutdown();
    }

    private List<DictBean> init(String dictType) {
        return this.dictService.getMstDict(dictType, "EN", "0");
    }
}
