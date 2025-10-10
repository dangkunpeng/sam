package com.sam.sam_parents.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.collect.Lists;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CaffineConfig {

    public static final String CACHE_NAME = "APP";
    public static final String CACHE_TEMP_NAME = "APP_TEMP";

    @Primary
    @Bean("customerCacheManager")
    public CacheManager customerCacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        List<CaffeineCache> cacheList = Lists.newArrayList();
        // 12小时有效
        cacheList.add(iapCache());
        // 10秒有效
        cacheList.add(tempCache());
        simpleCacheManager.setCaches(cacheList);
        return simpleCacheManager;
    }

    /**
     * 12小时有效
     *
     * @return
     */
    public CaffeineCache iapCache() {
        return new CaffeineCache(CACHE_NAME, Caffeine.newBuilder()
                .maximumSize(100000)
                .initialCapacity(1000)
                .expireAfterWrite(12, TimeUnit.HOURS)
                .recordStats()
                .build());
    }

    /**
     * 10秒有效
     *
     * @return
     */
    public CaffeineCache tempCache() {
        return new CaffeineCache(CACHE_TEMP_NAME, Caffeine.newBuilder()
                .maximumSize(100000)
                .initialCapacity(100)
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .recordStats()
                .build());
    }
}
