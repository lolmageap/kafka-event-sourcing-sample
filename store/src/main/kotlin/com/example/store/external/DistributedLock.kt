package com.example.store.external

import com.example.store.exception.RedisLockException
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toJavaDuration

@Component
class DistributedLock(
    private val redisson: RedissonClient,
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun <T> redLock(
        key: String,
        waitTime: Duration = 5000.milliseconds,
        leaseTime: Duration = 1000.milliseconds,
        block: () -> T,
    ): T {
        val lock = redisson.getLock(key)
        val waitTimeToLong = waitTime.toJavaDuration().toMillis()
        val leaseTimeToLong = leaseTime.toJavaDuration().toMillis()

        return try {
            val hasLock =
                lock.tryLock(waitTimeToLong, leaseTimeToLong, TimeUnit.MILLISECONDS)

            if (hasLock) block.invoke()
            else throw RedisLockException()
        } finally {
            lock.unlockAsync()
        }
    }
}