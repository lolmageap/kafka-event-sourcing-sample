package com.example.batch.jdbc.example.job

import com.example.batch.external.RedisReadService
import com.example.batch.jdbc.example.process.StockItemProcessor
import com.example.batch.model.Stock
import org.springframework.batch.core.Job
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.TaskletStep
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
class JdbcJobConfiguration(
    private val jobRepository: JobRepository,
    private val dataSource: DataSource,
    private val transactionManager: PlatformTransactionManager,
    private val redisReadService: RedisReadService,
) {

    @Bean(STOCK_JOB)
    fun job(): Job =
        JobBuilder(STOCK_JOB, jobRepository)
            .start(firstStep())
            .next(lastStep())
            .build()

    @Bean(STOCK_FIRST_STEP)
    fun firstStep(): TaskletStep =
        StepBuilder(
            STOCK_FIRST_STEP,
            jobRepository,
        ).chunk<Stock, Stock>(CHUNK_SIZE, transactionManager)
            .reader(stockItemReader())
            .processor(stockItemProcessor())
            .writer(stockItemWriter())
            .build()

    @Bean(STOCK_LAST_STEP)
    fun lastStep(): TaskletStep =
        StepBuilder(
            STOCK_LAST_STEP,
            jobRepository,
        ).tasklet({ _: StepContribution?, _: ChunkContext? ->
            RepeatStatus.FINISHED
        }, transactionManager)
            .build()

    @Bean(STOCK_ITEM_READER)
    fun stockItemReader(): JdbcCursorItemReader<Stock> =
        JdbcCursorItemReaderBuilder<Stock>()
            .name(STOCK_ITEM_READER)
            .dataSource(dataSource)
            .fetchSize(CHUNK_SIZE)
//            .sql("SELECT id, name, age FROM example")
            .rowMapper(BeanPropertyRowMapper(Stock::class.java))
            .build()

    @Bean(STOCK_ITEM_PROCESSOR)
    fun stockItemProcessor() =
        StockItemProcessor(redisReadService)

    @Bean(STOCK_ITEM_WRITER)
    fun stockItemWriter(): JdbcBatchItemWriter<Stock> =
        JdbcBatchItemWriterBuilder<Stock>()
            .dataSource(dataSource)
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider())
//            .sql("INSERT INTO example (id, name, age) VALUES (:id, :name, :age)")
            .beanMapped()
            .build()

    companion object {
        const val STOCK_JOB = "stockJob"
        const val STOCK_FIRST_STEP = "stockFirstStep"
        const val STOCK_LAST_STEP = "stockLastStep"
        const val STOCK_ITEM_READER = "stockItemReader"
        const val STOCK_ITEM_PROCESSOR = "stockItemProcessor"
        const val STOCK_ITEM_WRITER = "stockItemWriter"
        const val CHUNK_SIZE = 100
    }
}
