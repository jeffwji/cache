package com.wang.cache;

/**
 * Created by wangji on 2016/5/30.
 */
import com.wang.cache.interfaces.ICacheManager;
import com.wang.cache.memcache.MemcacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAutoConfiguration
@PropertySource(value = "classpath:config/memcache.properties")
public class CacheConfigure {
    @Value("${mem.cache.size}") Integer memCacheSize=300000;
    @Value("${mem.cache.timeToLiveSeconds}") Integer memCacheTimeToLiveSeconds = 0;
    @Value("${mem.cache.timeToIdleSeconds}") Integer memCacheTimeToIdleSeconds = 0;

    @Bean
    public ICacheManager getMemCacheManager(@Value("mem.cache.element.classType") String cacheObjectClassName) throws ClassNotFoundException {
        Class clazz = Class.forName(cacheObjectClassName);
        clazz.getClass();
        return new MemcacheManager();
    }
}
