package com.sam.sam_biz.tasks;

import com.sam.sap_commons.utils.FmtUtils;
import com.sam.sap_commons.utils.SysDefaults;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.sam.sap_commons.utils.SysDefaults.SYS_DEFAULT_DAY_PATTERN;

@Slf4j
@Service
public class CleanCache {
    private static final String KEY_PATTERN = "*{}*";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Scheduled(cron = "0 * * * * ?")
    public void run() {
        // 查询key
        String key = FmtUtils.fmtMsg(KEY_PATTERN, SysDefaults.minusDays(1, SYS_DEFAULT_DAY_PATTERN));
        this.deleteByPattern(key);
    }


    public void deleteByPattern(String pattern) {
        StopWatch stopWatch = new StopWatch("deleteByPattern - " + pattern);
        stopWatch.start("find keys");
        Set<String> keys = this.stringRedisTemplate.keys(pattern);
        if (CollectionUtils.isEmpty(keys)) {
            stopWatch.stop();
            log.info(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
            return;
        }
        stopWatch.stop();
        stopWatch.start(FmtUtils.fmtMsg("delete {} keys", keys.size()));
        this.stringRedisTemplate.delete(keys);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }
}
