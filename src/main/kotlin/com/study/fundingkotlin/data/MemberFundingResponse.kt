package com.study.fundingkotlin.data

import java.time.LocalDate

data class MemberFundingResponse(
    val fundingId : Long,
    val fundingPrice : Long,
    val memberId : Long,
    val productId : Long,
    val productName : String,
    val fundingDate : LocalDate
)
