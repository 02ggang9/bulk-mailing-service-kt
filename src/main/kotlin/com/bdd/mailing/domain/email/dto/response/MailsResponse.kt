package com.bdd.mailing.domain.email.dto.response

import com.bdd.mailing.domain.email.entity.Mail

data class MailsResponse(
    val id: Long,
    val title: String,
    val message: String,
) {
    companion object {
        fun from(mails: List<Mail>): List<MailsResponse> {
            return mails.map {
                MailsResponse(
                    id = it.id!!,
                    title = it.title,
                    message = it.message
                )
            }
        }
    }
}
