package com.study.fundingkotlin.exception.service.funding

import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.exception.service.ServiceException


open class FundingServiceException(
    override val errorCode : ErrorCode
) : ServiceException(errorCode)