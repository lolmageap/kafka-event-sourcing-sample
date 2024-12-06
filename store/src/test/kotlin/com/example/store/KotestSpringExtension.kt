package com.example.store

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringExtension

class KotestSpringExtension : AbstractProjectConfig() {
    override fun extensions() = listOf(SpringExtension)
}