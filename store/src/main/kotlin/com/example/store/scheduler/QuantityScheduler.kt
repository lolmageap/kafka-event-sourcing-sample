package com.example.store.scheduler

import com.example.store.external.RedisReadService
import com.example.store.external.RedisWriteService
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@EnableScheduling
class QuantityScheduler(
    private val redisReadService: RedisReadService,
    private val redisWriteService: RedisWriteService,
) {
    @Scheduled(fixedDelay = 100)
    fun updateQuantity() {
        // 스케줄러가 아니라 스프링 배치를 사용한다.
        // 회원과 상품의 정보를 가져온다.(회원 createdAt 기준으로 LIMIT 만큼 - 대략 100명) - 청크 단위 - item reader 사용
        // 회원과 상품의 아이디를 추출한다. - item processor 사용
        // 추출한 키로 val key = "$PRODUCT_KEY:$memberId:$productId" 이렇게 만든다.
        // 해당 키로 상품의 수량을 가져온다. - $PRODUCT_KEY:$memberId* 형태를 scan해서 배열에 모은다.
        // 수량을 가져왔다면 수량을 업데이트한다. - item writer 사용
    }

    private companion object {
        const val LIMIT = 100L
    }
}