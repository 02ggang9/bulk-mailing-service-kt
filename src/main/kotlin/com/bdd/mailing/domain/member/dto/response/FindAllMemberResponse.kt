package com.bdd.mailing.domain.member.dto.response

import com.bdd.mailing.domain.member.entity.Member

data class FindAllMemberResponse(
    val name: String,
    val email: String,
) {
    companion object {
        fun from(members: List<Member>): List<FindAllMemberResponse> {
            return members.map {
                FindAllMemberResponse(
                    it.name,
                    it.email
                )
            }.toList()
        }
    }
}
