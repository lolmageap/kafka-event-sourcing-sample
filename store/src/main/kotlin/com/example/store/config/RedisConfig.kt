package com.example.store.config

import com.example.store.property.RedisProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfig(
    private val redisProperty: RedisProperty,
) {
    @Bean
    fun connectionFactory(): LettuceConnectionFactory {
        val clusterNodes = redisProperty.nodes.split(",")
        val clusterConfig = RedisClusterConfiguration(clusterNodes)
        return LettuceConnectionFactory(clusterConfig)
    }

    @Bean
    fun redisTemplate() =
        RedisTemplate<String, String>().apply {
            connectionFactory = connectionFactory()
        }
}