package com.bdd.mailing.domain.member.controller

import com.bdd.mailing.domain.member.application.MemberService
import com.bdd.mailing.domain.member.dto.request.SaveMemberRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody

@Validated
@Controller
class MemberController(
    val memberService: MemberService,
) {

    @GetMapping("/members")
    fun findMembers(model: Model): String {
        val findMembers = memberService.findMembers()
        model.addAttribute("members", findMembers)
        return "members"
    }

    @GetMapping("/member")
    fun saveMemberForm(): String = "member"

    @ResponseBody
    @PostMapping("/member")
    fun saveMember(@RequestBody @Valid request: SaveMemberRequest): ResponseEntity<Void> {
        memberService.saveMember(request.nickname, request.email)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }


}
