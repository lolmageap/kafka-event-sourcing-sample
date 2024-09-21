package com.example.producer.config

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MapperConfig {
    @Bean
    fun modelMapper(): ModelMapper =
        ModelMapper()

    @Bean
    fun objectMapper(): ObjectMapper =
        ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
}