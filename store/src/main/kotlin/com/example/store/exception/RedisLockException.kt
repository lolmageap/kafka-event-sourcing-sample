package com.example.store.exception

class RedisLockException(
    override val message: String = "타임 아웃.... 분산락 획득 실패....ㅠㅠ",
): RuntimeException()