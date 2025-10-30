package com.sam.sam_biz.tasks;

import com.sam.sap_commons.utils.SysDefaults;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TaskMonitorKeyInCache {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Async
    @Scheduled(cron = "0/5 * * * * ?")
    public void run() {
        // 查询key
        monitorExpiringKeyInCache();
    }

    public void monitorExpiringKeyInCache() {
        // 使用SCAN命令查找不包含"20251027"的键
        String today = SysDefaults.minusDays(0);
        ScanOptions scanOptions = ScanOptions.scanOptions().match("*").count(100).build();
        // 遍历处理
        try (Cursor<String> cursor = stringRedisTemplate.scan(scanOptions)) {
            while (cursor.hasNext()) {
                String key = cursor.next();
                Long ttl = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);

                if (ttl > 10) {
                    if (ttl < 20) {
                        log.info("key {} expiring in {} seconds", key, ttl);
                    }
                    continue;
                }
                try {
                    String val = stringRedisTemplate.opsForValue().get(key);
                    log.info("ExpiringKeyInCache:key={},val={}", key, val);
                } catch (Exception e) {
                    String val = stringRedisTemplate.opsForList().getFirst(key);
                    log.info("ExpiringKeyInCache:key={},val={}", key, val);
                }

                // 后续可以按业务处理
            }
        }
    }
}
