package com.study.fundingkotlin.exception.entity


import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.exception.BusinessException

open class EntityNotFoundException(
    override val errorCode : ErrorCode
) : BusinessException(errorCode)
