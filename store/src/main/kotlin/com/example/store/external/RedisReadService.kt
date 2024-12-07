package com.example.store.external

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisReadService(
    private val redisTemplate: StringRedisTemplate,
) {
    fun getProductStock(
        memberId: String,
        productId: String,
    ): Int {
        val key = "$PRODUCT_KEY:$memberId:$productId"
        return redisTemplate.opsForValue().get(key)?.toInt() ?: 0
    }

    companion object {
        const val PRODUCT_KEY = "product"
    }
}