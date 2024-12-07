package com.example.store

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.store", "com.example.common", "com.example.redis"])
@ConfigurationPropertiesScan("com.example.store.property", "com.example.redis.property")
class StoreApplication

fun main(args: Array<String>) {
    runApplication<StoreApplication>(*args)
}