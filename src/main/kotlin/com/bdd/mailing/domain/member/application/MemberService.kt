package com.bdd.mailing.domain.member.application

import com.bdd.mailing.domain.member.dto.response.FindAllMemberResponse
import com.bdd.mailing.domain.member.entity.Member
import com.bdd.mailing.domain.member.entity.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MemberService(
    val memberRepository: MemberRepository,
) {

    @Transactional
    fun deleteMember(memberId: Long) {
        val findMember = memberRepository.findByIdOrNull(memberId)
            ?: throw IllegalArgumentException("해당하는 ID 값이 없습니다.")

        memberRepository.delete(findMember)
    }

    fun findMembers(): List<FindAllMemberResponse> =
        FindAllMemberResponse.from(memberRepository.findAll())

    @Transactional
    fun saveMember(nickName: String, email: String): Long {
        memberRepository.findByEmail(email)
            ?: throw IllegalArgumentException("동일한 이메일이 존재합니다.")

        return memberRepository.save(
            Member(
                name = nickName,
                email = email,
            )
        ).id!!
    }

}
