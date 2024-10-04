package com.example.producer.component

import com.example.producer.CacheKey.CHECKOUT
import com.example.producer.model.Checkout
import com.example.producer.model.CheckoutEvent
import com.example.producer.model.CheckoutRequest
import org.modelmapper.ModelMapper
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CheckoutService(
    private val checkoutRepository: CheckoutRepository,
    private val modelMapper: ModelMapper,
    private val kafkaEventSender : KafkaEventSender,
) {
    @Cacheable(CHECKOUT)
    fun save(
        request: CheckoutRequest
    ): Long {
        val entity = modelMapper.map(request, Checkout::class.java)
        val newCheckOut = checkoutRepository.save(entity)
        val checkOutEvent = CheckoutEvent.of(newCheckOut)
        kafkaEventSender.send(checkOutEvent)
        return newCheckOut.id
    }
}