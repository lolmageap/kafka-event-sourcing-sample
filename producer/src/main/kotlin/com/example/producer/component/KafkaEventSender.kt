package com.example.producer.component

import com.example.producer.model.KafkaEvent
import com.example.producer.model.Topic.CHECKOUT_COMPLETED
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
        model: KafkaEvent,
    ) {
        val jsonToModel = objectMapper.writeValueAsString(model)
        try {
            kafkaTemplate.send(CHECKOUT_COMPLETED, jsonToModel)
        } catch (e: Exception) {
            logger.error { "Error sending message to Kafka: ${e.message}" }
        }
        logger.info { "Message sent to Kafka: $jsonToModel" }
    }

//    fun <T> send(
//        model: T,
//    ) {
//        val jsonToModel = objectMapper.writeValueAsString(model)
//        kafkaTemplate.send(CHECKOUT_COMPLETED, jsonToModel)
//    }
}