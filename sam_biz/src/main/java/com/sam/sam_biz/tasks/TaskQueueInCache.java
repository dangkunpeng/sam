package com.sam.sam_biz.tasks;

import com.sam.sap_commons.redis.RedisCacheHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskQueueInCache {
    private static final String KEY_TASK = "TaskCleanCache";

    @Async
    @Scheduled(cron = "0/2 * * * * ?")
    public void run() {
        String key = RedisCacheHelper.newKey(KEY_TASK);
        RedisCacheHelper.leftPush(KEY_TASK, key);
        log.info("task {} started, size = {}", Thread.currentThread().getName(), RedisCacheHelper.listSize(KEY_TASK));
    }

    @Scheduled(cron = "0/3 * * * * ?")
    public void execute() {
        RedisCacheHelper.rightPop(KEY_TASK);
        log.info("task {} done, size = {}", Thread.currentThread().getName(), RedisCacheHelper.listSize(KEY_TASK));

    }
}
