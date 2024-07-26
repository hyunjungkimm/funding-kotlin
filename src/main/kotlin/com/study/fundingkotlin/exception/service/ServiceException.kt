package com.study.fundingkotlin.exception.service

import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.exception.BusinessException

open class ServiceException(
    override val errorCode : ErrorCode
) : BusinessException(errorCode)