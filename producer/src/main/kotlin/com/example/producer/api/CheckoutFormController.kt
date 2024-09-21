package com.example.producer.api

import mu.KotlinLogging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class CheckoutFormController {
    private val logger = KotlinLogging.logger {}

    @GetMapping("/checkout")
    fun checkoutForm(
        model: Model,
    ): String {
        logger.info { ">>> checkout form <<<" }
        return "checkout"
    }
}