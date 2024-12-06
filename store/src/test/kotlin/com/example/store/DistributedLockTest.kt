package com.example.store

import com.example.store.exception.RedisLockException
import com.example.store.external.DistributedLock
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration.Companion.seconds

@SpringBootTest
class DistributedLockTest(
    private val distributedLock: DistributedLock,
) : StringSpec({

    "redis 분산락 테스트" {
        val count = AtomicInteger(0)

        coroutineScope {
            repeat(10) {
                launch {
                    distributedLock.redLock(
                        REDIS_KEY,
                        waitTime = 5.seconds
                    ) { count.increase() }
                }
            }
            repeat(10) {
                launch {
                    distributedLock.redLock(
                        REDIS_KEY,
                        waitTime = 5.seconds
                    ) { count.increase() }
                }
            }
            repeat(10) {
                launch {
                    distributedLock.redLock(
                        REDIS_KEY,
                        waitTime = 5.seconds
                    ) { count.increase() }
                }
            }
            repeat(10) {
                launch {
                    distributedLock.redLock(
                        REDIS_KEY,
                        waitTime = 5.seconds
                    ) { count.increase() }
                }
            }
            repeat(10) {
                launch {
                    distributedLock.redLock(
                        REDIS_KEY,
                        waitTime = 5.seconds
                    ) { count.increase() }
                }
            }
        }

        count.get() shouldBe 50
    }

    "redis 분산락 타임 아웃 테스트" {
        val count = AtomicInteger(0)

        shouldThrow<RedisLockException> {
            coroutineScope {
                repeat(10) {
                    launch {
                        distributedLock.redLock(
                            REDIS_KEY,
                            waitTime = 1.seconds
                        ) {
                            count.increase()
                        }
                    }
                }
                repeat(10) {
                    launch {
                        distributedLock.redLock(
                            REDIS_KEY,
                            waitTime = 1.seconds
                        ) { count.increase() }
                    }
                }
                repeat(10) {
                    launch {
                        distributedLock.redLock(
                            REDIS_KEY,
                            waitTime = 1.seconds
                        ) { count.increase() }
                    }
                }
                repeat(10) {
                    launch {
                        distributedLock.redLock(
                            REDIS_KEY,
                            waitTime = 1.seconds
                        ) { count.increase() }
                    }
                }
                repeat(10) {
                    launch {
                        distributedLock.redLock(
                            REDIS_KEY,
                            waitTime = 1.seconds
                        ) {
                            count.increase()
                        }
                    }
                }
            }
        }
    }
})

private fun AtomicInteger.increase() {
    this.incrementAndGet()
    Thread.sleep(20)
}

private const val REDIS_KEY = "test-key"