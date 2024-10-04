package com.example.consumer.config

import KafkaConstant.BOOTSTRAP_SERVERS
import KafkaConstant.EARLIEST
import KafkaConstant.GROUP_ID
import com.fasterxml.jackson.databind.deser.std.StringDeserializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties

@EnableKafka
@Configuration
class KafkaConsumerConfig {
    /**
     * Kafka BackPressure Configuration
     *
     * flow
     * 1. 메시지 읽기: 3개의 thread가 병렬로 Kafka에 메시지를 요청
     *      3개로 설정한 이유는 적은 수의 Consumer로 관리해서 BackPressure를 방지하기 위함)
     *      적은 수의 Consumer로 관리하는 대신 메시지를 가져올 때 10개의 메시지를 가져오도록 설정
     *
     * 2. 메시지 처리: 메시지를 비즈니스 로직에 따라 처리
     * 3. ACK 전송: 메시지 처리가 성공적으로 끝나면 Consumer는 Acknowledgment.acknowledge() 메서드를 호출하여 해당 메시지에 대한 ACK 응답을 Kafka에 보냄
     * 4. 다음 메시지 처리: ACK가 전송하고 다음 메시지를 읽어오는 루틴이 반복함
     */
    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> =
        DefaultKafkaConsumerFactory(
            mapOf(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to BOOTSTRAP_SERVERS,
                ConsumerConfig.GROUP_ID_CONFIG to GROUP_ID,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java.name,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java.name,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to EARLIEST,
                ConsumerConfig.MAX_POLL_RECORDS_CONFIG to 10,
            )
        )

    @Bean
    fun kafkaListenerContainerFactory() =
        ConcurrentKafkaListenerContainerFactory<String, String>().apply {
            consumerFactory = consumerFactory()
            setConcurrency(3)
            containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        }
}