package com.study.fundingkotlin.config

import com.study.fundingkotlin.interceptor.HttpInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class HttpInterceptorConfig(val httpInterceptor: HttpInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        if (httpInterceptor != null) {
            registry.addInterceptor(httpInterceptor)
                .order(1)
                .addPathPatterns("/products/*/funding")
                .addPathPatterns("/products/member/fundingList")
        }
    }
}
