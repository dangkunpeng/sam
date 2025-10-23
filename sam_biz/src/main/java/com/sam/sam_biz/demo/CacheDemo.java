package com.sam.sam_biz.demo;

import com.sam.sap_commons.redis.RedisCacheHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.sam.sap_commons.utils.SysDefaults.CACHE_NAME;

@Slf4j
@Service
public class CacheDemo {

    @Cacheable(value = CACHE_NAME, key = "'helloCache-'+#key", unless = "#result==null")
    public String getCacheKey(String key) {
        log.info("No cache and  gen new key={}",key);
        return RedisCacheHelper.newKey(key);
    }
}
