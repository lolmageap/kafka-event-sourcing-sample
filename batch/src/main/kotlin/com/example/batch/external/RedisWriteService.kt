package com.example.batch.external

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisWriteService(
    private val redisTemplate: StringRedisTemplate,
) {
    fun deleteProductStock(
        memberId: String,
        productId: Long,
    ) {
        val key = "$PRODUCT_KEY:$memberId:$productId"
        redisTemplate.delete(key)
    }

    companion object {
        const val PRODUCT_KEY = "product"
    }
}