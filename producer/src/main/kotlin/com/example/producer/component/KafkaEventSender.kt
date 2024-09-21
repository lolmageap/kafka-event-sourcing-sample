package com.example.producer.component

import KafkaConstant.MY_TOPIC
import com.example.producer.model.CheckoutEvent
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaEventSender(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val objectMapper: ObjectMapper,
) {
    private val logger = KotlinLogging.logger {}

    fun send(
        event: CheckoutEvent,
    ) {
        val jsonToModel = objectMapper.writeValueAsString(event)
        try {
            kafkaTemplate.send(MY_TOPIC, jsonToModel)
        } catch (e: Exception) {
            logger.error { "Error sending message to Kafka: ${e.message}" }
        }
        logger.info { "Message sent to Kafka: $jsonToModel" }
    }
}