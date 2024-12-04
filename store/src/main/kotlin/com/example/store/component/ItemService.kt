package com.example.store.component

import com.example.store.model.PurchaseEvent
import com.example.store.model.PurchaseRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val itemRepository: ItemRepository,
    private val stockRepository: StockRepository,
    private val kafkaEventSender : KafkaEventSender,
) {
    fun purchase(
        request: PurchaseRequest,
    ): Long {
        val item = itemRepository.findByIdOrNull(request.itemId)
            ?: throw IllegalArgumentException("Item이 존재하지 않습니다.")

        val stock = stockRepository.findByItemId(request.itemId)
        if (stock.quantity < request.quantity) throw IllegalArgumentException("재고가 부족합니다.")

        val purchaseEvent = PurchaseEvent.of(item, request.quantity)
        kafkaEventSender.send(purchaseEvent)
        return item.id
    }
}