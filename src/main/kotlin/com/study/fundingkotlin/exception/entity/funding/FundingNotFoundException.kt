package com.study.fundingkotlin.exception.entity.funding

import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.exception.entity.EntityNotFoundException

class FundingNotFoundException(
    override val errorCode: ErrorCode
) : EntityNotFoundException(errorCode)
