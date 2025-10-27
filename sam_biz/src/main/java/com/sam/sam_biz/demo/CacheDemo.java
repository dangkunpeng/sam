package com.sam.sam_biz.demo;

import com.sam.sap_commons.redis.RedisCacheHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.sam.sap_commons.utils.SysDefaults.CACHE_NAME;

@Slf4j
@Service
public class CacheDemo {

    @Cacheable(value = {CACHE_NAME, "Cache"}, key = " 'CACHE_NAME-' + #root.methodName + '-' + #key", unless = "#result==null")
    public String getCacheKey(String key) {
        log.info("No cache key and  gen new key={}", key);
        return RedisCacheHelper.newKey(key);
    }

    @Cacheable(value = {CACHE_NAME, "Demo"}, key = " 'CACHE_NAME-' + #root.methodName + '-' + #key", unless = "#result==null")
    public String getDemoKey(String key) {
        log.info("No demo key and  gen new key={}", key);
        return RedisCacheHelper.newKey(key);
    }
}
