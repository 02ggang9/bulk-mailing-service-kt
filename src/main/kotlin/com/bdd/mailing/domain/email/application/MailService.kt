package com.bdd.mailing.domain.email.application

import com.bdd.mailing.domain.email.dto.response.MailsResponse
import com.bdd.mailing.domain.email.entity.Mail
import com.bdd.mailing.domain.email.entity.MailRepository
import com.bdd.mailing.domain.email.entity.MarkdownFormatConverter
import com.bdd.mailing.domain.member.application.MemberService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MailService(
    private val mailRepository: MailRepository,
    private val memberService: MemberService,
    private val gmailService: GmailService,
    private val markdownFormatConverter: MarkdownFormatConverter,
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

    @Transactional
    fun sendBulkMail(mailId: Long) {
        val findMembers = memberService.findMembers()
            .map { it.email }
            .toTypedArray()

//        findMembers.forEach { member -> gmailService.sendEmail(member, "test", "test") }
        gmailService.sendEmail(findMembers, "Test", "test")
    }

}
