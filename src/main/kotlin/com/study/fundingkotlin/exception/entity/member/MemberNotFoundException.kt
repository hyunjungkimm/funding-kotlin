package com.study.fundingkotlin.exception.entity.member

import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.exception.entity.EntityNotFoundException

open class MemberNotFoundException(
    override val errorCode: ErrorCode
) : EntityNotFoundException(errorCode)