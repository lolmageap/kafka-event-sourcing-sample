package com.example.batch.external

import com.example.batch.model.Stock
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisReadService(
    private val redisTemplate: StringRedisTemplate,
) {
    fun getProductStocks(
        memberId: String,
    ): List<Stock> {
        val stocks = mutableListOf<Stock>()
        val standard = "$PRODUCT_KEY:$memberId:"
        val key = "$PRODUCT_KEY:$memberId:*"

        val scanOptions =
            ScanOptions.scanOptions()
                .match(key)
                .count(100)
                .build()

        val cursor = redisTemplate.scan(scanOptions)

        while (cursor.hasNext()) {
            val keys = cursor.next()
            val value = redisTemplate.opsForValue().get(keys) ?: continue
            val productId = key.substringAfter(standard).toLong()
            stocks.add(
                Stock(
                    memberId = memberId.toLong(),
                    productId = productId,
                    stock = value.toLong(),
                )
            )
        }

        return stocks
    }

    companion object {
        const val PRODUCT_KEY = "product"
    }
}