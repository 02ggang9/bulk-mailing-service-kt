package com.bdd.mailing.domain.email.entity

interface MailFormatConverter {

    fun convert(message: String): String

}
