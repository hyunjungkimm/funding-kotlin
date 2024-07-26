package com.study.fundingkotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FundingKotlinApplication

fun main(args: Array<String>) {
    runApplication<FundingKotlinApplication>(*args)
}
