package com.example.producer.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Configuration
class RedisConfig {
    @Bean
    fun redisConnectionFactory() =
        LettuceConnectionFactory(
            RedisStandaloneConfiguration("localhost", 6379)
        )
}