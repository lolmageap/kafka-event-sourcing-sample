package com.example.consumer.component

import com.example.consumer.mapper.CheckoutMapper
import com.example.consumer.model.CheckoutRequest
import com.example.consumer.model.Shipment
import org.junit.jupiter.api.Test

class ModelMapperTest {

    @Test
    fun test() {
        val checkoutRequest = CheckoutRequest(
            productId = 1L,
            amount = 10L,
            memberId = 1L,
            shippingAddress = "Seoul",
        )

        val shipment =
            CheckoutMapper.map(
                source = checkoutRequest,
                destinationType = Shipment::class,
                1L,
            )

        require(shipment.id == 0L)
        require(shipment.checkoutId == 1L)
        require(shipment.memberId == 1L)
        require(shipment.productId == 1L)
        require(shipment.amount == 10L)
        require(shipment.shippingAddress == "Seoul")
    }
}