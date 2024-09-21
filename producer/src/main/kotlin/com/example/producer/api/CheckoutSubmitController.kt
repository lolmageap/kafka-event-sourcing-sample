package com.example.producer.api

import com.example.producer.component.CheckoutService
import com.example.producer.model.CheckoutRequest
import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class CheckoutSubmitController(
    private val checkoutService: CheckoutService,
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping("/checkout/submit")
    fun checkoutForm(
        @ModelAttribute checkOutRequest: CheckoutRequest,
        model: Model,
    ): String {
        logger.info { "checkout submit: $checkOutRequest" }
        val checkoutId = checkoutService.save(checkOutRequest)
        model.addAttribute("checkoutId", checkoutId)
        return "submitComplete"
    }
}