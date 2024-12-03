package com.example.producer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.example.common")
class StoreApplication

fun main(args: Array<String>) {
    runApplication<StoreApplication>(*args)
}
