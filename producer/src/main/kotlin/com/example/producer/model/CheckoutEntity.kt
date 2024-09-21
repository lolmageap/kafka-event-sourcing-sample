package com.example.producer.model

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.util.*

@Entity
@Table(name = "checkout")
class Checkout(
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