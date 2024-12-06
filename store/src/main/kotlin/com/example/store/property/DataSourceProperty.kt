package com.example.store.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.datasource")
data class DataSourceProperty(
    val master: DataSource,
    val slave: DataSource,
)

data class DataSource(
    val url: String,
    val username: String,
    val password: String,
    val driverClassName: String,
)