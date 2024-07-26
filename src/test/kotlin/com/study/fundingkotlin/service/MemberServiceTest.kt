package com.study.fundingkotlin.service

import com.study.fundingkotlin.entity.Member
import com.study.fundingkotlin.exception.entity.member.MemberNotFoundException
import com.study.fundingkotlin.repository.MemberRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class MemberServiceTest {

    @InjectMocks
    lateinit var memberService: MemberService

    @Mock
    lateinit var memberRepository: MemberRepository

    private fun makeMember(memberId: Long) = Member(
        memberId = memberId,
        memberName = "jung"
    )

    @Test
    @DisplayName("멤버 조회 - 성공")
    fun member_success() {
        val memberId = 1L

        Mockito.`when`(
            memberRepository.findById(memberId)
        ).thenReturn(
            Optional.ofNullable(makeMember(memberId))
        )

        val member: Member = memberService.getMember(memberId)

        assertThat(member.memberId).isEqualTo(memberId)
    }

    @Test
    @DisplayName("멤버 조회 - 실패")
    fun member_fail(){

        val memberId = 1L

        Mockito.`when`(
            memberRepository.findById(memberId)
        ).thenReturn(
            Optional.empty()
        )

        assertThrows<MemberNotFoundException> {
            memberService.getMember(memberId)
        }
    }
}