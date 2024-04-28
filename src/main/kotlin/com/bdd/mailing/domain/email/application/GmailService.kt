package com.bdd.mailing.domain.email.application

import com.bdd.mailing.domain.email.entity.MarkdownFormatConverter
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
class GmailService(
    private val javaMailSender: JavaMailSender,
    private val markdownFormatConverter: MarkdownFormatConverter,
) {

    fun sendEmail(to: String, subject: String, text: String) {
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        val convertedMessage = markdownFormatConverter.convert(text)

        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(convertedMessage, true)

        javaMailSender.send(message)
    }

    fun sendEmail(to: Array<String>, subject: String, text: String) {
        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)

        val convertedMessage = markdownFormatConverter.convert(text)

        helper.setTo(to)
        helper.setSubject(subject)
        helper.setText(convertedMessage, true)

        javaMailSender.send(message)
    }

}
