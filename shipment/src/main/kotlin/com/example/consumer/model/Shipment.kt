package com.example.consumer.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.util.*

@Entity
@Table(name = "shipment")
class Shipment(
    val checkoutId: Long,
    val memberId: Long,
    val productId: Long,
    val amount: Long,
    val shippingAddress: String,

    @CreatedDate
    val createdDate: Date,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}