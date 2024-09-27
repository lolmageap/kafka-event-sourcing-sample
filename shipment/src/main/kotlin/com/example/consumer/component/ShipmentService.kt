package com.example.consumer.component

import com.example.consumer.mapper.CheckoutMapper
import com.example.consumer.model.CheckoutRequest
import com.example.consumer.model.Shipment
import org.springframework.stereotype.Service

@Service
class ShipmentService(
    private val shipmentRepository: ShipmentRepository,
) {
    fun save(
        request: CheckoutRequest,
    ): Long {
        val shipment = CheckoutMapper.map(request, Shipment::class)
        val newShipment = shipmentRepository.save(shipment)
        return newShipment.id
    }
}