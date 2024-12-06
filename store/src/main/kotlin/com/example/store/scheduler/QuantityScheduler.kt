package com.example.store.scheduler

import com.example.store.external.RedisReadService
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@EnableScheduling
class QuantityScheduler(
    private val redisReadService: RedisReadService,
) {
    private var cursorIndex = 0L

    @Scheduled(fixedDelay = 100)
    fun updateQuantity() {
        TODO("Not yet implemented")
    }

    private companion object {
        const val PREFIX = "quantity"
        const val LIMIT = 100L
    }
}