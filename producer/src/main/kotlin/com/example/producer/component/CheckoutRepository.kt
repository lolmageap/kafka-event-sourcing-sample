package com.example.producer.component

import com.example.producer.model.Checkout
import org.springframework.data.jpa.repository.JpaRepository

interface CheckoutRepository: JpaRepository<Checkout, Long> {

}