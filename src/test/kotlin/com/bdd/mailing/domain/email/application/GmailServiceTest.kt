package com.bdd.mailing.domain.email.application

import com.bdd.mailing.domain.email.controller.MailController
import com.bdd.mailing.domain.email.entity.MarkdownFormatConverter
import com.icegreen.greenmail.configuration.GreenMailConfiguration
import com.icegreen.greenmail.junit5.GreenMailExtension
import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.GreenMailUtil
import com.icegreen.greenmail.util.ServerSetup
import com.icegreen.greenmail.util.ServerSetupTest
import org.awaitility.Awaitility
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mail.MailSender
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.test.context.TestPropertySource
import java.util.concurrent.TimeUnit

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = ["/application.yml"])
class GmailServiceTest(
    @Autowired
    private val gmailService: GmailService,

    @Autowired
    private val mailController: MailController,
) {

//    @MockBean
//    private lateinit var javaMailSender: JavaMailSender
//
//    @MockBean
//    private lateinit var markdownFormatConverter: MarkdownFormatConverter

    companion object {
        @JvmField
        @RegisterExtension
        val smtp: GreenMailExtension = GreenMailExtension(ServerSetup(2525, null, "smtp"))
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("username", "1234"))
            .withPerMethodLifecycle(false)
    }

//    @BeforeEach
//    fun setUp() {
//        val mailSender = JavaMailSenderImpl()
//        mailSender.host = "localhost"
//        mailSender.port = 2525
//
//        whenever(javaMailSender.createMimeMessage()).thenReturn(mailSender.createMimeMessage())
//        whenever(markdownFormatConverter.convert(any())).thenAnswer { it.arguments[0] as String }
//    }

//    @AfterEach
//    fun cleanUp() {
//        greenMail.stop()
//    }

    @Test
    fun singleRecipient() {

        val to = "0fficeg2@naver.com"
        val subject = "Test Subject"
        val text = "Test text"
//        for (i in 1..10) {
//            gmailService.sendEmail(to, subject, text)
//            mailController.sendBulkMail(2)
//        }
        Awaitility.await().atMost(2, TimeUnit.SECONDS).untilAsserted {
            mailController.sendBulkMail(2)
            val receivedMessages = smtp.receivedMessages
            assertEquals(4, receivedMessages.size)
//            assertEquals(subject, receivedMessages[0].subject)
        }
    }
}
