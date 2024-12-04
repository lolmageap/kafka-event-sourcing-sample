package com.example.store.component

import com.example.store.model.Stock
import org.springframework.data.jpa.repository.JpaRepository

interface StockRepository: JpaRepository<Stock, Long> {
    fun findByItemId(itemId: Long): Stock
}