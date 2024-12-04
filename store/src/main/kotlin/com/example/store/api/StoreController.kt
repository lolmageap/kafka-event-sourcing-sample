package com.example.store.api

import mu.KotlinLogging
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreController(
    private val redisTemplate: RedisTemplate<String, String>,
) {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/")
    fun index(): String {
        return "store"
    }

    @PostMapping("/store")
    fun store1(): String {
        redisTemplate.opsForValue().set("key", "value")
        return "store"
    }

    @GetMapping("/store")
    fun store2(): String {
        val value = redisTemplate.opsForValue().get("key")
        logger.info { "value: $value" }
        return "store"
    }
}