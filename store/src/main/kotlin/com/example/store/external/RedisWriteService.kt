package com.example.store.external

import com.example.redis.component.DistributedLock
import com.example.store.exception.SoldOutException
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisWriteService(
    private val redisTemplate: StringRedisTemplate,
    private val distributedLock: DistributedLock,
) {
    fun addProduct(
        memberId: String,
        productId: String,
        stock: Int,
    ) {
        val key = "$PRODUCT_KEY:$memberId:$productId"
        redisTemplate.opsForValue().set(key, stock.toString())
    }

    fun purchaseProduct(
        memberId: String,
        productId: String,
    ) {
        val key = "$PRODUCT_KEY:$memberId:$productId"

        distributedLock.redLock(key) {
            val currentStock =
                redisTemplate.opsForValue().get(key)
                    ?.toLong()
                    ?: throw SoldOutException()

            if (currentStock > 0) redisTemplate.opsForValue().set(key, (currentStock - 1).toString())
            else throw SoldOutException()
        }
    }

    fun refundProduct(
        memberId: String,
        productId: String,
    ) {
        val key = "$PRODUCT_KEY:$memberId:$productId"
        distributedLock.redLock(key) {
            val currentStock = redisTemplate.opsForValue().get(key)?.toLong()

            if (currentStock == null) redisTemplate.opsForValue().set(key, ONE_STOCK)
            else redisTemplate.opsForValue().set(key, (currentStock + 1).toString())
        }
    }

    companion object {
        const val PRODUCT_KEY = "product"
        const val ONE_STOCK = "1"
    }
}