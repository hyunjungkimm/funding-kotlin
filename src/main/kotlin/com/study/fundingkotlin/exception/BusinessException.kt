package com.study.fundingkotlin.exception

import com.study.fundingkotlin.error.ErrorCode

open class BusinessException(open val errorCode: ErrorCode) : RuntimeException()
