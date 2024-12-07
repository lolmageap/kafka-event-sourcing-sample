package com.example.redis.exception

class RedisLockException(
    override val message: String = "타임 아웃.... 분산락 획득 실패.... 다시 시도해주세요.",
): RuntimeException()