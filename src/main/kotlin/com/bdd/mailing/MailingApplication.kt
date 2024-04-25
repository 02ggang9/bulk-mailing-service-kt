package com.bdd.mailing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MailingApplication

fun main(args: Array<String>) {
	runApplication<MailingApplication>(*args)
}
