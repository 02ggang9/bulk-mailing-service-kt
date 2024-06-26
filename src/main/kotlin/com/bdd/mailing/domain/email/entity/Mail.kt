package com.bdd.mailing.domain.email.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Mail(

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "message", nullable = false)
    var message: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

) {

    fun update(title: String, message: String) {
        this.title = title
        this.message = message
    }

}
