package com.bdd.mailing.config.batch.job

import com.bdd.mailing.domain.member.entity.Member
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
class MailJobConfiguration(
    private val dataSource: DataSource
) {

    @Bean
    fun mailJob(jobRepository: JobRepository, platformTransactionalManager: PlatformTransactionManager): Job {
        return JobBuilder("mailJob", jobRepository)
            .start(sendBulkMailStep(jobRepository, platformTransactionalManager))
            .build()
    }

    @Bean
    fun sendBulkMailStep(jobRepository: JobRepository, platformTransactionalManager: PlatformTransactionManager): Step {
        return StepBuilder("sendMailStep", jobRepository)
            .chunk<Member, Member>(20, platformTransactionalManager)
            .reader(mailItemReader())
            .build()
    }

    @Bean
    fun mailItemReader(): ItemReader<Member> {
        return JdbcCursorItemReaderBuilder<Member>()
            .name("JdbcCursorItemReader")
            .fetchSize(20)
            .sql("SELECT id, name, email FROM member")
            .rowMapper { rs, rowNum -> Member(rs.getLong(1), rs.getString(2), rs.getLong(3)) }
            .dataSource(dataSource)
            .build()
    }

}
