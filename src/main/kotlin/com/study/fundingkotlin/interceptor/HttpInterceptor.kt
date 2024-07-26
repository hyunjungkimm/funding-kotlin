package com.study.fundingkotlin.interceptor


import com.study.fundingkotlin.entity.Member
import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.exception.entity.member.MemberNotFoundException
import com.study.fundingkotlin.exception.handler.GlobalExceptionHandler
import com.study.fundingkotlin.exception.service.user.UserServiceException
import com.study.fundingkotlin.repository.MemberRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class HttpInterceptor(val memberRepository : MemberRepository) : HandlerInterceptor {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val memberId: String = request.getHeader("X-MEMBER-ID")
        logger.info("memberId = {}", memberId)

        if (memberId != null) {
            val member: Member = memberRepository.findById(memberId.toLong())
                .orElseThrow { MemberNotFoundException(ErrorCode.NOT_SIGNED_UP_MEMBER) }

            request.setAttribute("memberId", member.memberId)
            return true
        } else {
            throw UserServiceException(ErrorCode.NOT_EXITS_MEMBER_ID_HEADER)
        }
    }
}
