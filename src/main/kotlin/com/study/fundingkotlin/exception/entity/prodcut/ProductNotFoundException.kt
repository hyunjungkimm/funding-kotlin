package com.study.fundingkotlin.exception.entity.prodcut

import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.exception.entity.EntityNotFoundException

open class ProductNotFoundException(
    override val errorCode : ErrorCode
) : EntityNotFoundException(errorCode)
