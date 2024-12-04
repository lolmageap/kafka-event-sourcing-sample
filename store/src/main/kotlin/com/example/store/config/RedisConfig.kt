package com.example.store.config

import com.example.store.property.RedisProperty
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
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

    @Bean
    fun redisCluster(): RedissonClient {
        val clusterConfig =
            Config().apply {
                useClusterServers()
                    .addNodeAddress(redisProperty.nodes)
                    .setScanInterval(2000)
                    .setConnectTimeout(100)
                    .setTimeout(3000)
                    .setRetryAttempts(3)
                    .setRetryInterval(1500)
            }
        return Redisson.create(clusterConfig)
    }
}