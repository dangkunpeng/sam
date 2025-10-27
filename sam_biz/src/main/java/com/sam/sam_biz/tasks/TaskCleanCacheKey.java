package com.sam.sam_biz.tasks;

import com.sam.sap_commons.utils.FmtUtils;
import com.sam.sap_commons.utils.SysDefaults;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sam.sap_commons.utils.SysDefaults.SYS_DEFAULT_DAY_PATTERN;

@Slf4j
@Service
public class TaskCleanCacheKey {
    private static final String KEY_PATTERN = "[^{}]+";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Async
    @Scheduled(cron = "0 0/5 * * * ?")
    public void run() {
        // 查询key
        deleteKeyExceptToday();
    }

    public void deleteKeyExceptToday() {
        // 使用SCAN命令查找不包含"20251027"的键
        String today = SysDefaults.minusDays(0, SYS_DEFAULT_DAY_PATTERN);
        Set<String> expiredKeys = new HashSet<>();
        ScanOptions scanOptions = ScanOptions.scanOptions().match("*" + StringUtils.substring(today, 0, 4)).count(100).build();
        try (Cursor<String> cursor = stringRedisTemplate.scan(scanOptions)) {
            while (cursor.hasNext()) {
                String key = cursor.next();
                if (key.contains(today)) {
                    continue;
                }
                expiredKeys.add(key);
            }
        }
        if (CollectionUtils.isEmpty(expiredKeys)){
            return;
        }
        log.info("expiredKeys to be deleted ={}",expiredKeys.stream().collect(Collectors.joining(",")));
        this.stringRedisTemplate.delete(expiredKeys);
    }

}
