package com.study.fundingkotlin.service

import com.study.fundingkotlin.entity.Member
import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.exception.entity.member.MemberNotFoundException
import com.study.fundingkotlin.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(val memberRepository: MemberRepository) {

    @Transactional(readOnly = true)
    fun getMember(memberId: Long): Member {
        return memberRepository.findById(memberId)
            .orElseThrow { MemberNotFoundException(ErrorCode.NOT_SIGNED_UP_MEMBER) }
    }
}
