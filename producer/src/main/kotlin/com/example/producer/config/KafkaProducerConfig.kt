package com.example.producer.config

import com.example.producer.model.KafkaEvent
import com.fasterxml.jackson.databind.JsonSerializer
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfig {
    @Bean
    fun kafkaProducerFactory(): ProducerFactory<String, KafkaEvent> =
        DefaultKafkaProducerFactory(
            mapOf(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVERS,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java.name,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java.name,
                ProducerConfig.ACKS_CONFIG to "all",
                ProducerConfig.RETRIES_CONFIG to "100"
            )
        )

    @Bean
    fun jsonKafkaTemplate() =
        KafkaTemplate(kafkaProducerFactory())

    companion object {
        private const val BOOTSTRAP_SERVERS = "localhost:9092"
        private const val GROUP_ID = "test-group"
        private const val MY_TOPIC = "my-topic"
        private const val EARLIEST = "earliest"
    }
}