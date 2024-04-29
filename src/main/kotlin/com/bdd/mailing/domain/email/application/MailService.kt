package com.bdd.mailing.domain.email.application

import com.bdd.mailing.domain.email.dto.response.MailsResponse
import com.bdd.mailing.domain.email.entity.Mail
import com.bdd.mailing.domain.email.entity.MailRepository
import com.bdd.mailing.domain.email.entity.MarkdownFormatConverter
import com.bdd.mailing.domain.member.application.MemberService
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.context.ApplicationContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MailService(
    private val mailRepository: MailRepository,
    private val memberService: MemberService,
    private val gmailService: GmailService,
    private val markdownFormatConverter: MarkdownFormatConverter,
    private val jobLauncher: JobLauncher,
    private val applicationContext: ApplicationContext,
) {

    @Transactional
    fun saveMail(title: String, message: String): Long {
        return mailRepository.save(
            Mail(
                title = title,
                message = message,
            )
        ).id!!
    }

    fun findMails(): List<MailsResponse> {
        return MailsResponse.from(mailRepository.findAll())
    }

    fun findMail(mailId: Long): Mail {
        return mailRepository.findByIdOrNull(mailId)
            ?: throw IllegalArgumentException("$mailId 에 해당하는 메일이 없습니다.")
    }

    @Transactional
    fun updateMail(mailId: Long, title: String, message: String) {
        val findMail = mailRepository.findByIdOrNull(mailId)
            ?: throw IllegalArgumentException("저장된 메일이 없습니다.")

        findMail.update(title, message)
    }

    @Transactional
    fun deleteMail(mailId: Long) {
        mailRepository.deleteById(mailId)
    }

    fun convertSaveMailMessage(mailId: Long): String {
        val findMail = (mailRepository.findByIdOrNull(mailId)
            ?: throw IllegalArgumentException("$mailId 에 해당하는 메일을 찾을 수 없습니다."))

        return markdownFormatConverter.convert(findMail.message)
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    fun sendBulkMail(mailId: Long) {
        val findMail = (mailRepository.findByIdOrNull(mailId)
            ?: throw IllegalArgumentException("$mailId 에 해당하는 메일을 찾을 수 없습니다."))

        startBulkMailJob(findMail.id!!, findMail.title, findMail.message)
    }

    private fun startBulkMailJob(mailId: Long, title: String, message: String) {
        val findJob = applicationContext.getBean("mailJob", Job::class.java)

        val jobParameter = JobParametersBuilder()
            .addLong("mailId", mailId)
            .addString("mailSubject", title)
            .addString("mailMessage", message)
            .toJobParameters()

        this.jobLauncher.run(findJob, jobParameter)
    }

}
