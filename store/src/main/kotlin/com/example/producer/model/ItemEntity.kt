package com.example.producer.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.data.annotation.CreatedDate
import java.math.BigDecimal
import java.util.*

@Entity
class Item(
    val memberId: Long,
    val price: BigDecimal,
    var status: ItemStatus,

    @CreatedDate
    val createdDate: Date,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    init {
        require(memberId > 0) { "회원 ID는 0보다 커야 합니다." }
        require(price >= BigDecimal.ZERO) { "가격은 0보다 같거나 커야 합니다." }
    }
}