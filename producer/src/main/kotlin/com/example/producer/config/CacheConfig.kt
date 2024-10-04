package com.example.producer.config

import com.example.producer.CacheKey
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import java.time.Duration

@EnableCaching
@Configuration
class CacheConfig {
    @Bean
    fun redisConnectionFactory() =
        LettuceConnectionFactory(
            RedisStandaloneConfiguration("localhost", 6379)
        )

    @Bean
    fun redisCacheManagerBuilderCustomizer() =
        RedisCacheManagerBuilderCustomizer { builder ->
            builder
                .withCacheConfiguration(
                    CacheKey.CHECKOUT,
                    RedisCacheConfiguration
                        .defaultCacheConfig()
                        .entryTtl(
                            Duration.ofSeconds(10)
                        )
                )
                .withCacheConfiguration(
                    CacheKey.PRODUCT,
                    RedisCacheConfiguration
                        .defaultCacheConfig()
                        .entryTtl(
                            Duration.ofMinutes(1)
                        )
                )
        }

    @Bean
    fun localCacheManager(): CacheManager {
        return object : CacheManager {
            private val logger = KotlinLogging.logger {}

            private val delegate =
                RedisCacheManager
                    .builder(redisConnectionFactory())
                    .enableCreateOnMissingCache()
                    .build()

            override fun getCache(
                name: String,
            ): Cache? =
                delegate.getCache(name).also {
                    if (it != null) logger.info { "Cache Hit : $name" }
                    else logger.info { "Cache Miss : $name" }
                }

            override fun getCacheNames(): MutableCollection<String> =
                delegate.cacheNames
        }
    }
}