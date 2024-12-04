package com.example.store

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.example.common")
@ConfigurationPropertiesScan("com.example.store.property")
class StoreApplication

fun main(args: Array<String>) {
    runApplication<StoreApplication>(*args)
}