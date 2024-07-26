package com.study.fundingkotlin.error

data class ErrorResponse(
    val code : String,
    val message : String
) {
    companion object {
        fun of(errorCode: ErrorCode): ErrorResponse {
            return ErrorResponse(
                code = errorCode.code,
                message = errorCode.message
            )
        }
    }
}
