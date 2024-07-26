package com.study.fundingkotlin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.study.fundingkotlin.data.ProductFundingResponse
import com.study.fundingkotlin.data.ProductResponse
import com.study.fundingkotlin.entity.Member
import com.study.fundingkotlin.repository.MemberRepository
import com.study.fundingkotlin.service.ProductService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate
import java.util.*

@WebMvcTest(ProductController::class)
@MockBean(JpaMetamodelMappingContext::class)
internal class ProductControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var productService: ProductService

    @MockBean
    lateinit var memberRepository: MemberRepository

    @Test
    @DisplayName("펀딩 제품 전체 조회하기 - getAllProducts")
    fun allProducts() {
        val memberId = 1L


        Mockito.`when`(
            memberRepository.findById(memberId)
        ).thenReturn(
            Optional.ofNullable(makeMember())
        )

        Mockito.`when`(
            productService.getProducts()
        ).thenReturn(
            productResponseList()
        )

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v2/products")
                .header("X-MEMBER-ID", memberId)
                .contentType("application/json")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(
                MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(productResponseList()))
            )
    }


    @Test
    @DisplayName("펀딩 제품 전체 조회하기 - getAllProductWithPAgeByQueryMethod")
    fun allProductWithPAgeByQueryMethod() {
            val memberId = 1L
            val productResponsePage: Page<ProductResponse> = PageImpl<ProductResponse>(productResponseList())

            Mockito.`when`(
                memberRepository.findById(memberId)
            ).thenReturn(
                Optional.ofNullable(makeMember())
            )

            Mockito.`when`(
                productService.getProductProgress(0, 5)
            ).thenReturn(
                productResponsePage
            )


            mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v3/products")
                    .header("X-MEMBER-ID", memberId)
                    .param("size", 0.toString())
                    .param("page", 5.toString())
                    .contentType("application/json")
            )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(productResponsePage)))
        }


    @DisplayName("펀딩 제품 전체 조회하기 - getAllProduct")
    @ParameterizedTest
    @ValueSource(longs = [1, 2, 3])
    fun getAllProduct(memberId: Long) {
        val productResponseSlice: Slice<ProductResponse> = PageImpl<ProductResponse>(productResponseList())

        Mockito.`when`(
            memberRepository.findById(memberId)
        ).thenReturn(
            Optional.ofNullable(makeMember())
        )

        Mockito.`when`(
            productService.getAllProducts(PageRequest.of(0, 5))
        ).thenReturn(
            productResponseSlice
        )

        val params = String.format("?page=%d&size=%d", 0, 5)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v4/products$params")
                .header("X-MEMBER-ID", memberId)
                .contentType("application/json")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(productResponseSlice)))
    }


    private fun makeMember() = Member(
        memberId = 1L,
        memberName = "jung"
    )

    private fun productResponseList(): List<ProductResponse> {
        val productFundingResponse: ProductFundingResponse = ProductFundingResponse(1L)

        val productResponse: ProductResponse = ProductResponse(
            productId = 1L,
            productName= "제품명",
            targetFundingPrice = 10000L,
            fundingMemberNumber = 0,
            startDate = LocalDate.now(),
            finishDate = LocalDate.now().plusDays(3),
            fundingList = listOf(productFundingResponse)
        )

        return listOf(productResponse)
    }
}