package com.example.consumer.model

data class CheckoutRequest(
    val memberId: Long,
    val productId: Long,
    val amount: Long,
    val shippingAddress: String,
)