package com.study.fundingkotlin.service

import com.study.fundingkotlin.data.MemberFundingResponse
import com.study.fundingkotlin.entity.Funding
import com.study.fundingkotlin.entity.Member
import com.study.fundingkotlin.entity.Product
import com.study.fundingkotlin.exception.entity.funding.FundingNotFoundException
import com.study.fundingkotlin.exception.entity.member.MemberNotFoundException
import com.study.fundingkotlin.repository.FundingRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class FundingServiceTest {
    @InjectMocks
    lateinit var fundingService: FundingService

    @Mock
    lateinit var fundingRepository: FundingRepository

    private fun makeMember(memberId: Long) = Member(
        memberId = memberId,
        memberName = "jung"
    )

    private fun makeProduct(productId: Long) = Product(
        productId = productId,
        productName = "제품명",
        totalFundingPrice = 1000L,
        targetFundingPrice = 10000L,
        fundingStatus = "펀딩중",
        startDate = LocalDate.now(),
        finishDate = LocalDate.now().plusDays(3),
        fundingMemberNumber = 0
    )

    private fun makeFunding(fundingId: Long) = Funding(
        fundingId = fundingId,
        fundingPrice = 1000L,
        fundingDate = LocalDate.now(),
        member = makeMember(1L),
        product = makeProduct(1L)
    )


    @DisplayName("펀딩하기 - 정상")
    @Test
    fun productFunding() {
        val fundingId = 1L
        val funding: Funding = makeFunding(fundingId)

        Mockito.`when`(
            fundingRepository.save(funding)
        ).thenReturn(
            funding
        )

        Mockito.`when`(
            fundingRepository.findById(fundingId)
        ).thenReturn(
            Optional.ofNullable(funding)
        )

        fundingService.productFunding(funding)
        val saveFunding: Funding = fundingService.getFunding(fundingId)
        assertThat(saveFunding.fundingId).isEqualTo(fundingId)
    }

    @Test
    @DisplayName("펀딩 조회 - 실패")
    fun getFunding_fail() {
        val fundingId = 1L

        Mockito.`when`(
            fundingRepository.findById(fundingId)
        ).thenReturn(
            Optional.empty()
        )

        assertThrows<FundingNotFoundException> {
            fundingService.getFunding(fundingId)
        }
    }

    @Test
    fun productFundingList() {
        val memberId = 1L
        val fundingId = 1L
        val member: Member = makeMember(memberId)

        Mockito.`when`(
            fundingRepository.findAllByMember(member)
        ).thenReturn(
            listOf(makeFunding(fundingId))
        )

        val productFundingList: List<MemberFundingResponse> = fundingService.getProductFundingList(member)
        assertThat(productFundingList.size).isEqualTo(1)
    }
}