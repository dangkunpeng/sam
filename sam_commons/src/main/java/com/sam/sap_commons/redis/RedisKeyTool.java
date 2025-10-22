// redis-spring-boot-starter/src/main/java/com/example/redisstarter/RedisClient.java
package com.sam.sap_commons.redis;

import com.sam.sap_commons.utils.SysDefaults;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.sam.sap_commons.utils.SysDefaults.COUNT_LENGTH;
import static com.sam.sap_commons.utils.SysDefaults.PAD_CHAR;

@Component
public class RedisKeyTool {
    @Resource
    private RedisTemplate<String, Integer> redisTemplate;

    public String newKey(String id) {
        // 这里可以调用 KeyTool 来生成主键
        StringBuilder result = new StringBuilder();
        // 时间戳
        result.append(id);
        result.append(SysDefaults.nowDay());
        // 获取时间戳的使用次数
        Long counter = redisTemplate.opsForValue().increment(result.toString());
        // 拼接上序号
        result.append(StringUtils.leftPad(String.valueOf(counter), COUNT_LENGTH, PAD_CHAR));
        return result.toString();
    }
}
