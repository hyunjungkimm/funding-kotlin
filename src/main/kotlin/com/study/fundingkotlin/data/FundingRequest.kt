package com.study.fundingkotlin.data

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Positive
import org.jetbrains.annotations.NotNull


data class FundingRequest(
    @field:NotNull
    @field:Min(value = 1000)
    @field:Positive
    var fundingPrice: Long
)