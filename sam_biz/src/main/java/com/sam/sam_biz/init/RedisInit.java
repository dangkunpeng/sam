package com.sam.sam_biz.init;

import com.sam.sap_commons.redis.RedisKeyTool;
import com.sam.sap_commons.redis.RedisMsgQueue;
import com.sam.sap_commons.utils.FmtUtils;
import com.sam.sap_commons.utils.SysDefaults;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.sam.sap_commons.utils.SysDefaults.SYS_DEFAULT_DAY_PATTERN;

@Order(99)
@Slf4j
@Component
public class RedisInit implements ApplicationRunner {

    private static final String KEY_PATTERN = "*{}*";
    @Resource
    private RedisKeyTool redisKeyTool;
    @Resource
    private RedisMsgQueue redisMsgQueue;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 查询key
        this.deleteByPattern(FmtUtils.fmtMsg(KEY_PATTERN, SysDefaults.minusDays(1, SYS_DEFAULT_DAY_PATTERN)));
        log.info("================ {} =================", "helloworld");
        for (int i = 0; i < 10; i++) {
            String key = redisKeyTool.newKey("key");
            String val = redisKeyTool.newKey("val");
            stringRedisTemplate.opsForValue().set(key, val);
            log.info("RedisInit key={}, val={}", key, stringRedisTemplate.opsForValue().get(key));
        }
        String key = redisKeyTool.newKey("mqKey");
        for (int i = 0; i < 10; i++) {

            String val = redisKeyTool.newKey("mqVal");
            redisMsgQueue.leftPush(key, val);
            log.info("mq key={}, val={}", key, redisMsgQueue.rightPop(key));
        }
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
