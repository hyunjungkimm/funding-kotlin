package com.study.fundingkotlin.service

import com.study.fundingkotlin.data.MemberFundingResponse
import com.study.fundingkotlin.entity.Funding
import com.study.fundingkotlin.entity.Member
import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.exception.entity.funding.FundingNotFoundException
import com.study.fundingkotlin.repository.FundingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FundingService(val fundingRepository: FundingRepository) {

    @Transactional
    fun productFunding(funding: Funding) {
        fundingRepository.save(funding)
    }

    @Transactional(readOnly = true)
    fun getProductFundingList(member: Member): List<MemberFundingResponse> {
        return fundingRepository.findAllByMember(member).map { funding ->
            MemberFundingResponse(
                fundingId = funding.fundingId!!,
                fundingPrice = funding.fundingPrice,
                fundingDate = funding.fundingDate!!,
                memberId = funding.member.memberId!!,
                productId = funding.product.productId!!,
                productName = funding.product.productName

            )
        }
    }

    @Transactional(readOnly = true)
    fun getFunding(fundingId: Long): Funding {
        return fundingRepository.findById(fundingId)
            .orElseThrow { FundingNotFoundException(ErrorCode.NOT_FOUND_FUNDING) }
    }
}
