package com.example.store.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.redis.cluster")
data class RedisProperty(
    val nodes: List<String>,
) {
    val clusterNodes: List<String>
        get() = nodes.map { it.replace("redis://", "") }
}