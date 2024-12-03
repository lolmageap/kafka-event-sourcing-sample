package com.example.producer.model

import java.math.BigDecimal
import java.util.*

data class PurchaseRequest(
    val memberId: Long,
    val itemId: Long,
    val quantity: Int,
)

data class PurchaseEvent(
    val itemId: Long,
    val memberId: Long,
    val quantity: Int,
    val price: BigDecimal,
    val createdDate: Date,
) {
    companion object {
        fun of(
            item: Item,
            quantity: Int,
        ) =
            with(item) {
                PurchaseEvent(
                    itemId = id,
                    memberId = memberId,
                    quantity = quantity,
                    price = price,
                    createdDate = createdDate,
                )
            }
    }
}