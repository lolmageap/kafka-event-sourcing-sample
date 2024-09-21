package com.example.consumer.component

import KafkaConstant.GROUP_ID
import KafkaConstant.MY_TOPIC
import com.example.consumer.model.CheckoutRequest
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaEventReader(
    private val objectMapper: ObjectMapper,
    private val shipmentService: ShipmentService,
) {
    private val logger = KotlinLogging.logger {}

    @KafkaListener(
        topics = [MY_TOPIC],
        groupId = GROUP_ID,
    )
    fun listenMessage(
        message: String,
    ) {
        try {
            val request = objectMapper.readValue(message, CheckoutRequest::class.java)
            logger.info { ">>> Received request: $request <<<" }
            shipmentService.save(request)
        } catch (e: Exception) {
            logger.error { ">>> Failed to parse message: $message due to ${e.localizedMessage} <<<" }
        }
    }
}