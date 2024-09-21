package com.example.producer.api

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
class CheckoutSubmitControllerTest(
    @Autowired private val mockMvc: MockMvc,
) {
    @Test
    fun submit() {
        mockMvc.perform(
            post("/checkout/submit")
                .param("memberId", "1")
                .param("productId", "1")
                .param("amount", "1")
                .param("shippingAddress", "123 Main St")
        ).andExpect(status().isOk)
            .andDo(::println)
    }
}