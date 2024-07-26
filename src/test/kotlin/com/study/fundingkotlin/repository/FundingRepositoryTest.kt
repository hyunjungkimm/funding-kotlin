package com.study.fundingkotlin.repository

import com.study.fundingkotlin.entity.Funding
import com.study.fundingkotlin.entity.Member
import com.study.fundingkotlin.entity.Product
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.time.LocalDate

@DataJpaTest
internal class FundingRepositoryTest {

    @Autowired
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

    @DisplayName("펀딩 저장 후 총 개수 조회")
    @Test
    fun findAllByMember() {
        val memberId = 1L
        val fundingId = 1L
        fundingRepository.save<Funding>(makeFunding(fundingId))
        Assertions.assertThat(fundingRepository.findAllByMember(makeMember(memberId)))
            .isNotNull()
            .hasSizeGreaterThanOrEqualTo(1)
    }
}