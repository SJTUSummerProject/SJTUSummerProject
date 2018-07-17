package sjtusummerproject.ticketmicroservice.Config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

//@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600 * 12)//最大过期时间
@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        //设置缓存过期时间
        Map<String, Long> expires = new HashMap<>();
        expires.put("12h", 3600 * 12L);
        expires.put("1h", 3600 * 1L);
        expires.put("10m", 60 * 10L);
        rcm.setExpires(expires);
//        rcm.setDefaultExpiration(60 * 60 * 12);//默认过期时间
        return rcm;
    }
}
