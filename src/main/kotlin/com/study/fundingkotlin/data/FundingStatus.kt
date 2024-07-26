package com.study.fundingkotlin.data


enum class FundingStatus(
    val status: String
) {
    IN_PROGRESS("펀딩중"),
    COMPLETED("펀딩완료")
}
