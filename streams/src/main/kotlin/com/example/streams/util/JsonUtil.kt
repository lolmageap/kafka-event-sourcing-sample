package com.example.streams.util

import com.jayway.jsonpath.JsonPath
import java.math.BigDecimal

object JsonUtil {
    object Product {
        fun getProductId(
            json: String
        ): Long =
            JsonPath.parse(json).read("$.productId", Long::class.java)

        fun getAmount(
            json: String
        ): Long =
            JsonPath.parse(json).read("$.amount", Long::class.java)

        fun getSendingJson(
            productId: Long,
            amount: Long
        ): String =
            """
            {
                "productId": $productId,
                "amount": $amount,
                "createdDate": "${System.currentTimeMillis()}"
            }
            """.trimIndent()
    }

    object PurchaseItem {
        fun getItemId(
            json: String
        ): Long =
            JsonPath.parse(json).read("$.itemId", Long::class.java)

        fun getPrice(
            json: String
        ): Long =
            JsonPath.parse(json).read("$.price", Long::class.java)

        fun sendingJson(
            itemId: Long,
            totalPrice: Long
        ): String =
            """
            {
                "itemId": $itemId,
                "totalPrice": $totalPrice,
                "createdDate": "${System.currentTimeMillis()}"
            }
            """.trimIndent()
    }
}