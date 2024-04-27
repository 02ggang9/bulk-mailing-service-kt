package com.bdd.mailing.domain.email.entity

import org.springframework.data.jpa.repository.JpaRepository

interface MailRepository: JpaRepository<Mail, Long> {
}
