package com.study.fundingkotlin.controller

import com.study.fundingkotlin.data.FundingRequest
import com.study.fundingkotlin.data.FundingResponse
import com.study.fundingkotlin.data.MemberFundingResponse
import com.study.fundingkotlin.service.FundingFacadeService
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import lombok.RequiredArgsConstructor
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequiredArgsConstructor
@Validated
class FundingController(val fundingFacadeService: FundingFacadeService){

    @PostMapping("/products/{product_id}/funding")
    fun productFunding(
        @RequestAttribute memberId: Long,
        @PathVariable(name = "product_id") @Positive productId: Long,
        @RequestBody @Valid fundingRequest: FundingRequest
    ): FundingResponse {
        return fundingFacadeService.productFunding(memberId, productId, fundingRequest)
    }

    @GetMapping("/products/member/fundingList")
    fun getMemberFundingList(@RequestAttribute memberId: Long): List<MemberFundingResponse> {
        return fundingFacadeService.getProductFundingList(memberId)
    }
}
