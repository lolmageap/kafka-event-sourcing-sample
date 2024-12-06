package com.example.store.external

import org.springframework.data.redis.connection.DataType
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@EnableScheduling
class RedisReadService(
    private val stringRedisTemplate: StringRedisTemplate
) {
    @Scheduled(fixedDelay = 100)
    fun updateQuantity(
        prefix: String,
        limit: Long
    ) {
        val scanOptions = ScanOptions.scanOptions()
            .count(limit)
            .match("$prefix:*")
            .type(DataType.STRING)
            .build()

        stringRedisTemplate.scan(scanOptions)
    }
}
