package com.sam.sap_commons.redis;

import com.sam.sap_commons.utils.JsonUtil;
import com.sam.sap_commons.utils.SysDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.sam.sap_commons.utils.SysDefaults.COUNT_LENGTH;
import static com.sam.sap_commons.utils.SysDefaults.PAD_CHAR;

@Service
public class RedisCacheHelper {
    private static StringRedisTemplate stringRedisTemplate;

    @Autowired
    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        RedisCacheHelper.stringRedisTemplate = stringRedisTemplate;
    }

    // 设置缓存（带过期时间）
    public static <T> void set(String key, T value) {
        set(key, value, 12, TimeUnit.HOURS);
    }

    // 设置缓存（带过期时间）
    public static <T> void set(String key, T value, long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JsonUtil.toJsonString(value), time, unit);
    }

    // 获取缓存（解决缓存穿透：缓存空值）
    public <T> T get(String key, Class<T> type) {
        String json = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(json)) {
            // 判断是否为缓存的空值（防止缓存穿透）
            // 缓存不存在，返回null，由调用方决定是否从数据源加载
            return null;
        }
        return JsonUtil.toObj(json, type);
    }

    // 删除缓存
    public static Boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }

    // 判断缓存是否存在
    public static Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * leftPush 和 rightPop 可以实现一个简单的队列
     *
     * @param key
     * @param value
     */
    public static void leftPush(String key, String value) {
        stringRedisTemplate.opsForList().leftPush(key, value);
    }

    public static Object rightPop(String key) {
        return stringRedisTemplate.opsForList().rightPop(key);
    }

    public static String newKey(String id) {
        // 这里可以调用 KeyTool 来生成主键
        StringBuilder result = new StringBuilder();
        // 时间戳
        result.append(id);
        result.append(SysDefaults.nowDay());
        // 获取时间戳的使用次数
        Long counter = stringRedisTemplate.opsForValue().increment(result.toString());
        // 拼接上序号
        result.append(StringUtils.leftPad(String.valueOf(counter), COUNT_LENGTH, PAD_CHAR));
        return result.toString();
    }
}