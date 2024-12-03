package com.example.streams.config

import com.example.streams.util.JsonUtil
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.kstream.TimeWindows
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class StreamListener(
    private val streamsBuilder: StreamsBuilder,
) {

    @Bean
    fun kStream(): KStream<String, String> {
        return streamsBuilder.stream<String, String>(INPUT_TOPIC).also {
            it.map { key, value ->
                KeyValue(
                    JsonUtil.Product.getProductId(key),
                    JsonUtil.Product.getAmount(value),
                )
            }.groupByKey(
                Grouped.with(
                    Serdes.Long(),
                    Serdes.Long(),
                )
            ).windowedBy(
                TimeWindows.of(
                    Duration.ofMinutes(1)
                )
            ).reduce { accumulator, newValue ->
                accumulator + newValue
            }.toStream { key, _ ->
                key.key()
            }.mapValues(
                JsonUtil.Product::getSendingJson
            ).selectKey { _, _ ->
                null
            }.to(
                OUTPUT_TOPIC,
                Produced.with(null, Serdes.String())
            )
        }
    }

    private companion object {
        const val INPUT_TOPIC = "checkout.complete.v1"
        const val OUTPUT_TOPIC = "checkout.productId.aggregated.v1"
    }
}