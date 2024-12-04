package com.example.store.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.data.redis.connection.RedisNode

@ConfigurationProperties(prefix = "spring.data.redis.cluster")
data class RedisProperty(
    val nodes: String,
) {
    val redisNodes: List<RedisNode>
        get() =
            nodes.split(",").map {
                val (host, port) = it.split(":")
                RedisNode(host, port.toInt())
            }
}