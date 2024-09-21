package com.example.consumer.component

import com.example.consumer.model.CheckoutRequest
import com.example.consumer.model.Shipment
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class ShipmentService(
    private val shipmentRepository: ShipmentRepository,
    private val modelMapper: ModelMapper,
) {
    fun save(
        request: CheckoutRequest,
    ): Long {
        val shipment = modelMapper.map(request, Shipment::class.java)
        val newShipment = shipmentRepository.save(shipment)
        return newShipment.id
    }
}