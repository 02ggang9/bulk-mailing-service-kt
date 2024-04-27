package com.bdd.mailing.domain.email.dto.request

import jakarta.validation.constraints.NotEmpty

data class SaveMailRequest(

    @field:NotEmpty(message = "제목을 입력해주세요.")
    val title: String,

    @field:NotEmpty(message = "본문을 입력해주세요.")
    val text: String,

)
