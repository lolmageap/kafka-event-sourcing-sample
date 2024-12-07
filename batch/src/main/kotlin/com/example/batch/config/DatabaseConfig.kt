package com.example.batch.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
class DatabaseConfig {
    @Bean
    fun jdbcTemplate(
        dataSource: DataSource,
    ) =
        JdbcTemplate(dataSource)
}