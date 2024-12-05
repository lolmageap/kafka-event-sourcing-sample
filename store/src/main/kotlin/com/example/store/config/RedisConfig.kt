package com.example.store.config

import com.example.store.property.RedisProperty
import io.lettuce.core.SocketOptions
import io.lettuce.core.cluster.ClusterClientOptions
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import java.time.Duration

@Configuration
class RedisConfig(
    private val redisProperty: RedisProperty,
) {
    @Bean
    fun connectionFactory(): LettuceConnectionFactory {
        val clusterConfiguration = RedisClusterConfiguration(redisProperty.nodes)

        val socketOptions =
            SocketOptions.builder()
                .connectTimeout(Duration.ofMillis(500))
                .keepAlive(true)
                .build()

        val clusterTopologyRefreshOptions =
            ClusterTopologyRefreshOptions.builder()
                .dynamicRefreshSources(true)
                .enableAllAdaptiveRefreshTriggers()
                .enablePeriodicRefresh(Duration.ofMinutes(30))
                .build()

        val clusterClientOptions =
            ClusterClientOptions.builder()
                .topologyRefreshOptions(clusterTopologyRefreshOptions)
                .socketOptions(socketOptions)
                .build()

        val lettuceClientConfiguration =
            LettuceClientConfiguration.builder()
                .clientOptions(clusterClientOptions)
                .commandTimeout(Duration.ofSeconds(3))
                .build()

        return LettuceConnectionFactory(clusterConfiguration, lettuceClientConfiguration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> =
        StringRedisTemplate().apply {
            connectionFactory = connectionFactory()
            valueSerializer = GenericJackson2JsonRedisSerializer()
        }

//    @Bean
//    fun redisCluster(): RedissonClient {
//        val clusterConfig =
//            Config().apply {
//                useClusterServers()
//                    .addNodeAddress(*redisProperty.nodes.toTypedArray())
//                    .setScanInterval(2000)
//                    .setConnectTimeout(500)
//                    .setTimeout(3000)
//                    .setRetryAttempts(3)
//                    .setRetryInterval(1500)
//            }
//        return Redisson.create(clusterConfig)
//    }
}