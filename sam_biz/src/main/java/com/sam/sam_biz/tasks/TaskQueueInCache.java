package com.sam.sam_biz.tasks;

import com.sam.sap_commons.utils.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TaskQueueInCache {
    private static final String KEY_TASK = "TaskCleanCache";

    @Async
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void run() {
        String key = RedisHelper.newKey(KEY_TASK);
        RedisHelper.leftPush(KEY_TASK, key);
        log.info("task {} started, size = {}", Thread.currentThread().getName(), RedisHelper.listSize(KEY_TASK));
    }

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void execute() {
        RedisHelper.rightPop(KEY_TASK);
        log.info("task {} done, size = {}", Thread.currentThread().getName(), RedisHelper.listSize(KEY_TASK));

    }
}
