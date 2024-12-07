package com.example.batch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication(
    exclude = [
        FlywayAutoConfiguration::class,
    ],
    scanBasePackages = [
        "com.example.batch",
        "com.example.redis",
    ]
)
@ConfigurationPropertiesScan
class BatchApplication

fun main(args: Array<String>) {
    runApplication<BatchApplication>(*args)
}