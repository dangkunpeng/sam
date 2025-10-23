package com.sam.sam_biz.tasks;

import com.sam.sap_commons.redis.RedisCacheHelper;
import com.sam.sap_commons.utils.FmtUtils;
import com.sam.sap_commons.utils.SysDefaults;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.sam.sap_commons.utils.SysDefaults.SYS_DEFAULT_DAY_PATTERN;

@Slf4j
@Service
public class TaskCleanCache {
    private static final String KEY_PATTERN = "*{}*";
    private static final String KEY_TASK = "TaskCleanCache";
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Async
    @Scheduled(cron = "0/5 * * * * ?")
    public void run() {
        RedisCacheHelper.leftPush(KEY_TASK, RedisCacheHelper.newKey(KEY_TASK));
        log.info("task {} started, size = {}", Thread.currentThread().getName(), stringRedisTemplate.opsForList().size(KEY_TASK));

        try {
            Thread.sleep(16000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 查询key
        String key = FmtUtils.fmtMsg(KEY_PATTERN, SysDefaults.minusDays(1, SYS_DEFAULT_DAY_PATTERN));
        this.deleteByPattern(key);
    }


    public void deleteByPattern(String pattern) {
        RedisCacheHelper.rightPop(KEY_TASK);
        log.info("task {} done, size = {}", Thread.currentThread().getName(), stringRedisTemplate.opsForList().size(KEY_TASK));
        Set<String> keys = this.stringRedisTemplate.keys(pattern);
        if (CollectionUtils.isEmpty(keys)) {
            return;
        }
        this.stringRedisTemplate.delete(keys);
    }
}
