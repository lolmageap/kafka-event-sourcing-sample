package com.example.store.exception

data class SoldOutException(
    override val message: String = "상품이 모두 판매 되었습니다.",
) : RuntimeException(message)