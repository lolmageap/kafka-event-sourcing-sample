package com.example.consumer.mapper

import com.example.consumer.model.CheckoutRequest
import com.example.consumer.model.Shipment
import kotlin.reflect.KClass

object CheckoutMapper {
    fun map(
        source: CheckoutRequest,
        destinationType: KClass<Shipment>,
        checkoutId: Long = 0L,
    ) = Shipment(
            memberId = source.memberId,
            productId = source.productId,
            amount = source.amount,
            shippingAddress = source.shippingAddress,
            checkoutId = checkoutId,
        )
}