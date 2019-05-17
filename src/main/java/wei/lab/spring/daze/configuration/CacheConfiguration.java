package wei.lab.spring.daze.configuration;

import wei.lab.spring.daze.cache.MyConcurrentMapCache;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * created by weixuecai on 2018/9/18
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    @Bean
    CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(cache5Secs()));
//        System.out.println("cache configuration is invoked.");

        return cacheManager;
    }

    @Bean
    Cache cache5Secs() {
        Cache cache = new MyConcurrentMapCache("cache5Secs");
        return cache;
    }
}
