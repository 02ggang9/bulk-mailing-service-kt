package com.bdd.mailing.domain.member.entity

import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<Member, Long> {

    fun findByEmail(email: String): Member?

}
