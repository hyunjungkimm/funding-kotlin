package com.study.fundingkotlin.error

enum class ErrorCode(val message: String, val code: String) {
    NOT_SIGNED_UP_MEMBER("가입된 사용자가 아닙니다.", "NOT_SIGNED_UP_MEMBER"),
    NOT_EXITS_MEMBER_ID_HEADER("Header에 member id가 없습니다.", "NOT_EXITS_MEMBER_ID_HEADER"),
    REQUIRED_VALUE("펀딩금액은 필수이며 1000원 이상이여야 합니다.", "REQUIRED_VALUE"),
    INVALID_INPUT_VALUE("Parameter가 유효하지 않습니다.", "INVALID_INPUT_VALUE"),
    NOT_FOUND_PRODUCT("존재하지 않는 상품입니다.", "NOT_FOUND_PRODUCT"),
    NOT_FOUND_FUNDING("존재하지 않는 펀딩입니다.", "NOT_FOUND_FUNDING"),
    SOLD_OUT("펀딩 모집이 완료되었습니다.", "SOLD_OUT"),
    FUNDING_AMOUNT_IS_GREATER_THAN_CAN_BE_FUNDED_AMOUNT(
        "펀딩 금액이 펀딩 가능한 잔여 금액을 초과하였습니다",
        "FUNDING_AMOUNT_IS_GREATER_THAN_CAN_BE_FUNDED_AMOUNT"
    )
}
