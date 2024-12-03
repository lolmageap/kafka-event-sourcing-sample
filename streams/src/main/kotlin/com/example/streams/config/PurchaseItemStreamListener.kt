package com.example.streams.config

import KafkaConstant.PURCHASE_ITEM_TOPIC
import com.example.streams.util.JsonUtil
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

@Configuration
class PurchaseItemStreamListener(
    private val streamsBuilder: StreamsBuilder,
) {
    @Bean
    fun sumOfItemPrice(): KStream<String, String> =
        streamsBuilder.stream<String, String>(PURCHASE_ITEM_TOPIC).also {
            it.map { _, value ->
                KeyValue(
                    JsonUtil.PurchaseItem.getItemId(value),
                    JsonUtil.PurchaseItem.getPrice(value),
                )
            }
                .groupByKey(Serdes.Long(), Serdes.Long())
                .windowSize(1.minutes)
                .sum()
                .restoreOriginalKey()
                .mapValues(JsonUtil.PurchaseItem::sendingJson)
                .noKey()
                .to(
                    "purchaseItem.sum",
                    Produced.with(null, Serdes.String())
                )
        }
}

private fun <K, V> KStream<K, V>.groupByKey(
    keySerde: Serde<K>,
    valueSerde: Serde<V>,
): KGroupedStream<K, V> =
    this.groupByKey(
        Grouped.with(
            keySerde,
            valueSerde,
        )
    )

private fun <K, V> KGroupedStream<K, V>.windowSize(
    size: kotlin.time.Duration,
): TimeWindowedKStream<K, V> =
    windowedBy(
        TimeWindows.ofSizeWithNoGrace(
            size.toJavaDuration()
        )
    )

private fun <K> TimeWindowedKStream<K, Long>.sum(): KTable<Windowed<K>, Long> =
    this.reduce { accumulator, newValue ->
        accumulator + newValue
    }

private fun <K, V> KTable<Windowed<K>, V>.restoreOriginalKey(): KStream<K, V> =
    this.toStream { key, _ -> key.key() }

private fun <T, R> KStream<T, R>.noKey(): KStream<Nothing, R> =
    selectKey { _, _ -> null }