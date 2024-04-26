package com.bdd.mailing.domain.member.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class SaveMemberRequest(
    @field:NotEmpty(message = "닉네임을 입력해주세요.")
    val nickname: String,

    @field:Email(message = "올바른 이메일을 입력해주세요.")
    val email: String,
)
