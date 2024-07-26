package com.study.fundingkotlin.exception.service.user

import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.exception.service.ServiceException

open class UserServiceException(
    override val errorCode : ErrorCode
) : ServiceException(errorCode)