package com.example.producer.model

import java.util.*

data class CheckoutRequest(
    val memberId: Long,
    val productId: Long,
    val amount: Long,
    val shippingAddress: String,
)

data class CheckoutEvent private constructor(
    val checkoutId: Long,
    val memberId: Long,
    val productId: Long,
    val amount: Long,
    val shippingAddress: String,
    val createdDate: Date,
) {
    companion object {
        fun of(
            checkout: Checkout,
        ) =
            with(checkout) {
                CheckoutEvent(
                    checkoutId = id,
                    memberId = memberId,
                    productId = productId,
                    amount = amount,
                    shippingAddress = shippingAddress,
                    createdDate = createdDate,
                )
            }
    }
}