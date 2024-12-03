package com.example.producer.component

import com.example.producer.model.Stock
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository: JpaRepository<Stock, Long> {
    fun findByItemId(itemId: Long): Stock
}