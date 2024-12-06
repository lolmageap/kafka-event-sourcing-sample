package com.example.store.config

import com.example.store.property.DataSourceProperty
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import javax.sql.DataSource

@Configuration
@EnableJpaAuditing
class DatabaseConfig(
    private val dataSourceProperty: DataSourceProperty,
) {
    @Bean(MASTER_DATA_SOURCE)
    fun masterDataSource(): HikariDataSource =
        DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .url(dataSourceProperty.master.url)
            .username(dataSourceProperty.master.username)
            .password(dataSourceProperty.master.password)
            .driverClassName(dataSourceProperty.master.driverClassName)
            .build()

    @Bean(SLAVE_DATA_SOURCE)
    fun slaveDataSource(): HikariDataSource =
        DataSourceBuilder.create()
            .type(HikariDataSource::class.java)
            .url(dataSourceProperty.slave.url)
            .username(dataSourceProperty.slave.username)
            .password(dataSourceProperty.slave.password)
            .driverClassName(dataSourceProperty.slave.driverClassName)
            .build()
            .apply { this.isReadOnly = true }

    @Bean
    @DependsOn(MASTER_DATA_SOURCE, SLAVE_DATA_SOURCE)
    fun routingDataSource(
        @Qualifier(MASTER_DATA_SOURCE) masterDataSource: DataSource,
        @Qualifier(SLAVE_DATA_SOURCE) slaveDataSource: DataSource,
    ): DataSource {
        val dataSources = hashMapOf<Any, Any>().apply {
            this[MASTER] = masterDataSource
            this[SLAVE] = slaveDataSource
        }

        return RoutingDataSource().apply {
            setTargetDataSources(dataSources)
            setDefaultTargetDataSource(masterDataSource)
        }
    }

    @Bean
    @Primary
    @DependsOn(ROUTING_DATA_SOURCE)
    fun dataSource(routingDataSource: DataSource) =
        LazyConnectionDataSourceProxy(routingDataSource)
}