package com.bdd.mailing.domain.email.application

import com.bdd.mailing.domain.email.dto.response.MailsResponse
import com.bdd.mailing.domain.email.entity.Mail
import com.bdd.mailing.domain.email.entity.MailRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MailService(
    private val mailRepository: MailRepository,
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


}
