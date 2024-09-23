package com.example.streams.util

import com.jayway.jsonpath.JsonPath

object JsonUtil {
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