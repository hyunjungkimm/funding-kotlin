package com.study.fundingkotlin.data

import java.time.LocalDate

data class ProductResponse (
    val productId: Long,
    val productName: String,
    val targetFundingPrice : Long,
    val startDate: LocalDate,
    val finishDate: LocalDate,
    val fundingMemberNumber: Int?= 0,
    val fundingList: List<ProductFundingResponse> = ArrayList()
)
