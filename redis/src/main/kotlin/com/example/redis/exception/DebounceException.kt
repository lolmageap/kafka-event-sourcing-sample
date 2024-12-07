package com.example.redis.exception

data class DebounceException(
    override val message: String = "짧은 시간 내에 너무 많은 요청이 들어왔습니다. 잠시 후 다시 시도해주세요.",
) : Exception(message)