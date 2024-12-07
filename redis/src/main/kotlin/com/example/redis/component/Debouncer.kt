package com.example.redis.component

import com.example.redis.exception.DebounceException
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import kotlin.time.Duration
import kotlin.time.toJavaDuration

@Component
class Debouncer(
    private val redisTemplate: StringRedisTemplate,
) {
    fun debounce(
        key: String,
        timeout: Duration,
    ) {
        val alreadyExists = redisTemplate.opsForValue().setIfAbsent(key, EMPTY_VALUE, timeout.toJavaDuration())
        if (alreadyExists == false) throw DebounceException()
    }

    companion object {
        const val EMPTY_VALUE = ""
    }
}