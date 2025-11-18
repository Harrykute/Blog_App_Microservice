package com.blogapp.postservice.config;

import java.time.Duration;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

public class RedisConfiguration {
	// 🔥 DEFAULT cache configuration (applies to all caches)
    @Bean
    public RedisCacheConfiguration defaultCacheConfig() {

        // Serializer → converts Java objects to JSON before storing in Redis
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

        return RedisCacheConfiguration
                .defaultCacheConfig()

                // Set serializer for Redis values (JSON format)
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(serializer)
                )

                // Set default expiry (TTL) for all caches → 10 minutes
                .entryTtl(Duration.ofMinutes(5))

                // Do NOT cache null values
                .disableCachingNullValues();
    }


    // 🔥 Cache Manager — controls all Redis caches
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        // Custom TTL for specific caches
        RedisCacheConfiguration postsCacheConfig = defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30)); // posts live longer

        RedisCacheConfiguration usersCacheConfig = defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(15)); // user cache 15 mins

        // Map cache names → configurations
        Map<String, RedisCacheConfiguration> cacheConfigs =
                Map.of(
                        "posts", postsCacheConfig,
                        "users", usersCacheConfig
                );

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
                .cacheDefaults(defaultCacheConfig())           // default config for others
                .withInitialCacheConfigurations(cacheConfigs)  // custom configs
                .build();
    }
}
