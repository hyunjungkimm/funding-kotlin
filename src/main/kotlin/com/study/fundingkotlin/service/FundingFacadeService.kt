package com.study.fundingkotlin.service

import com.study.fundingkotlin.data.FundingRequest
import com.study.fundingkotlin.data.FundingResponse
import com.study.fundingkotlin.data.FundingStatus
import com.study.fundingkotlin.data.MemberFundingResponse
import com.study.fundingkotlin.entity.Funding
import com.study.fundingkotlin.entity.Member
import com.study.fundingkotlin.entity.Product
import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.exception.service.funding.FundingServiceException
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FundingFacadeService(
    private val productService: ProductService,
    private val memberService: MemberService,
    private val fundingService: FundingService
) {

    private val logger = LoggerFactory.getLogger(FundingService::class.java)

    @Transactional
    fun productFunding(memberId: Long, productId: Long, fundingRequest: FundingRequest): FundingResponse {
        val product: Product = productService.getProduct(productId)

        if (product.fundingStatus.equals(FundingStatus.IN_PROGRESS.status)) {
            checkFundingStatus(product, fundingRequest)

            val updateProduct: Product = productService.getProduct(productId)
            val member: Member = memberService.getMember(memberId)


            val funding = Funding(
                fundingPrice =  fundingRequest.fundingPrice,
                product = updateProduct,
                member = member
            )
            fundingService.productFunding(funding)

            product.totalFundingPrice += fundingRequest.fundingPrice
            productService.updateProduct(product)

            return FundingResponse(fundingStatus = "펀딩 성공")
        } else {
            logger.info("펀딩 종료")
            throw FundingServiceException(ErrorCode.SOLD_OUT)
        }
    }

    private fun checkFundingStatus(product: Product, fundingRequest: FundingRequest) {
        val currentFundingPrice: Long = product.totalFundingPrice
        val fundingPriceSum: Long = currentFundingPrice + fundingRequest.fundingPrice

        if (fundingPriceSum > product.targetFundingPrice) {
            logger.info("{} 원 펀딩 가능합니다.", fundingPriceSum - product.targetFundingPrice)
            throw FundingServiceException(ErrorCode.FUNDING_AMOUNT_IS_GREATER_THAN_CAN_BE_FUNDED_AMOUNT)
        } else if (fundingPriceSum == product.targetFundingPrice) {
            product.fundingStatus = FundingStatus.COMPLETED.status
        } else {
            product.fundingStatus = FundingStatus.IN_PROGRESS.status
        }
    }

    fun getProductFundingList(memberId: Long): List<MemberFundingResponse> {
        val member: Member = memberService.getMember(memberId)
        return fundingService.getProductFundingList(member)
    }
}
