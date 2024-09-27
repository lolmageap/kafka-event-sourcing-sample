package com.example.consumer.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.Instant
import java.util.*

@Entity
@Table(name = "shipment")
class Shipment(
    var checkoutId: Long,
    val memberId: Long,
    val productId: Long,
    val amount: Long,
    val shippingAddress: String,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreatedDate
    val createdDate: Date = Date.from(Instant.now())
}