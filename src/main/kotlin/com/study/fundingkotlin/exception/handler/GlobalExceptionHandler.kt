package com.study.fundingkotlin.exception.handler


import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.error.ErrorResponse
import com.study.fundingkotlin.exception.entity.EntityNotFoundException
import com.study.fundingkotlin.exception.service.ServiceException
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.UnexpectedTypeException
import org.hibernate.exception.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException


@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(value = [MethodArgumentNotValidException::class, HttpMessageNotReadableException::class])
    protected fun handleMethodArgumentNotValidException(e: HttpServletRequest): ResponseEntity<ErrorResponse> {
        logger.error("handleMethodArgumentNotValidException",e)
        val errorResponse: ErrorResponse = ErrorResponse.of(ErrorCode.REQUIRED_VALUE)
        return ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    protected fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<ErrorResponse> {
        logger.error("handleConstraintViolationException", e)
        val errorResponse: ErrorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE)
        return ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [UnexpectedTypeException::class, MethodArgumentTypeMismatchException::class])
    protected fun UnexpectedTypeException(e: HttpServletRequest): ResponseEntity<ErrorResponse> {
        logger.error("UnexpectedTypeException", e)
        val response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE)
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR) //500
    }

    @ExceptionHandler(value = [EntityNotFoundException::class])
    fun EntityNotFoundException(e: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        logger.error("EntityNotFoundException", e)
        val errorResponse: ErrorResponse = ErrorResponse.of(e.errorCode)
        return ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [ServiceException::class])
    fun ServiceException(e: ServiceException): ResponseEntity<ErrorResponse> {
        logger.error("ServiceException", e)
        val errorResponse: ErrorResponse = ErrorResponse.of(e.errorCode)
        return ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
