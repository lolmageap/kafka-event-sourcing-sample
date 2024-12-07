package com.example.batch.jdbc.example.process

import com.example.batch.external.RedisReadService
import com.example.batch.model.Stock
import org.springframework.batch.item.ItemProcessor

class StockItemProcessor(
    private val redisReadService: RedisReadService,
) : ItemProcessor<Stock, Stock> {
    override fun process(
        item: Stock,
    ) = TODO()
}