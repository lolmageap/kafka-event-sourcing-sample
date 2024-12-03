package com.example.producer.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Stock(
    val itemId: Long,
    val quantity: Int,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    init {
        require(itemId > 0) { "상품 ID는 0보다 커야 합니다." }
        require(quantity >= 0) { "수량은 0보다 같거나 커야 합니다" }
    }
}