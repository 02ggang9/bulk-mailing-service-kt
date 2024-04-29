package com.bdd.mailing.domain.email.controller

import com.bdd.mailing.domain.email.application.GmailService
import com.bdd.mailing.domain.email.application.MailService
import com.bdd.mailing.domain.email.dto.request.SaveMailRequest
import com.bdd.mailing.domain.member.dto.request.SaveMemberRequest
import jakarta.validation.Valid
import org.apache.coyote.Response
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import kotlin.time.measureTime

@Validated
@Controller
class MailController(
    private val mailService: MailService,
    private val gmailService: GmailService,
) {

    @GetMapping("/news-mail")
    fun mailForm(): String = "save-mail"

    @PostMapping("/news-mail")
    fun saveMail(@RequestBody @Valid saveMailRequest: SaveMailRequest): ResponseEntity<String> {
        mailService.saveMail(saveMailRequest.title, saveMailRequest.text)
        val headers = HttpHeaders()
        headers.add("Location", "/news-mails")
        return ResponseEntity(headers, HttpStatus.CREATED)
    }

    @GetMapping("/news-mail/{mailId}")
    fun updateMailForm(@PathVariable(name = "mailId") mailId: Long, model: Model): String {
        val findMail = mailService.findMail(mailId)
        model.addAttribute("title", findMail.title)
        model.addAttribute("message", findMail.message)
        return "update-mail"
    }

    @GetMapping("/news-mail/preview/{mailId}")
    fun savedMailForm(@PathVariable(name = "mailId") mailId: Long, model: Model): String {
        val savedForm = mailService.convertSaveMailMessage(mailId)
        model.addAttribute("message", savedForm)
        return "convert-mail"
    }

    @PatchMapping("/news-mail/{mailId}")
    fun updateMail(
        @RequestBody @Valid request: SaveMailRequest,
        @PathVariable(name = "mailId") mailId: Long,
    ): ResponseEntity<String> {
        mailService.updateMail(mailId, request.title, request.text)
        val httpHeaders = HttpHeaders()
        httpHeaders.add("Location", "/news-mails")
        return ResponseEntity(httpHeaders, HttpStatus.CREATED)
    }

    @DeleteMapping("/news-mail/{mailId}")
    fun deleteMail(@PathVariable(name = "mailId") mailId: Long): ResponseEntity<Void> {
        mailService.deleteMail(mailId)

        val httpHeaders = HttpHeaders()
        httpHeaders.add("Location", "/news-mails")

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @GetMapping("/news-mails")
    fun mailForm(model: Model): String {
        val findMails = mailService.findMails()
        model.addAttribute("mails", findMails)
        return "save-mails"
    }

    @PostMapping("/bulk-mail/{mailId}")
    fun sendBulkMail(@PathVariable(name = "mailId") mailId: Long): ResponseEntity<Void> {
        mailService.sendBulkMail(mailId)
        return ResponseEntity.status(HttpStatus.OK).build()

    }
}
