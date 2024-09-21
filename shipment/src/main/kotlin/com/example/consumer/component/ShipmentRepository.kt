package com.example.consumer.component

import com.example.consumer.model.Shipment
import org.springframework.data.jpa.repository.JpaRepository

interface ShipmentRepository: JpaRepository<Shipment, Long> {
}