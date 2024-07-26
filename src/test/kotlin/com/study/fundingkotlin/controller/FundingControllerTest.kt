package com.study.fundingkotlin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.study.fundingkotlin.data.FundingRequest
import com.study.fundingkotlin.data.FundingResponse
import com.study.fundingkotlin.data.MemberFundingResponse
import com.study.fundingkotlin.entity.Member
import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.exception.service.funding.FundingServiceException
import com.study.fundingkotlin.repository.MemberRepository
import com.study.fundingkotlin.service.FundingFacadeService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate
import java.util.*

@WebMvcTest(FundingController::class)
@MockBean(JpaMetamodelMappingContext::class)
internal class FundingControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var fundingFacadeService: FundingFacadeService

    @MockBean
    private lateinit var memberRepository: MemberRepository

    @Test
    @DisplayName("내가 펀딩한 리스트 조회 api - 성공")
    fun memberFundingList_success() {
        val memberId = 1L
        val memberFundingResponse = MemberFundingResponse(
            fundingId = 1L,
            fundingPrice = 1000L,
            memberId = 1L,
            productId = 1L,
            productName = "product",
            fundingDate = LocalDate.now()
        )

        val responseList = listOf(memberFundingResponse)

        Mockito.`when`(fundingFacadeService.getProductFundingList(memberId)).thenReturn(responseList)
        Mockito.`when`(memberRepository.findById(memberId)).thenReturn(Optional.of(makeMember()))

        mockMvc.perform(
            MockMvcRequestBuilders.get("/products/member/fundingList")
                .header("X-MEMBER-ID", memberId)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(responseList)))
    }

    @Test
    @DisplayName("펀딩하기 api - 성공")
    fun productFundingTest_success() {
        val memberId = 1L
        val productId = 1L
        val fundingRequest = FundingRequest(1000L)
        val fundingResponse = FundingResponse("펀딩 성공")

        Mockito.`when`(fundingFacadeService.productFunding(memberId, productId, fundingRequest)).thenReturn(fundingResponse)
        Mockito.`when`(memberRepository.findById(memberId)).thenReturn(Optional.of(makeMember()))

        val content = objectMapper.writeValueAsString(fundingRequest)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/products/$productId/funding")
                .header("X-MEMBER-ID", memberId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(fundingResponse)))
    }

    @Test
    @DisplayName("펀딩하기 api - 실패")
    fun productFundingTest_fail() {
        val memberId = 1L
        val productId = 1L
        val fundingRequest = FundingRequest(100L)

        Mockito.`when`(fundingFacadeService.productFunding(memberId, productId, fundingRequest))
            .thenThrow(FundingServiceException(ErrorCode.REQUIRED_VALUE))
        Mockito.`when`(memberRepository.findById(memberId)).thenReturn(Optional.of(makeMember()))

        val content = objectMapper.writeValueAsString(fundingRequest)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/products/$productId/funding")
                .header("X-MEMBER-ID", memberId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("펀딩금액은 필수이며 1000원 이상이여야 합니다."))
    }

    private fun makeMember() = Member(
        memberId = 1L,
        memberName = "jung"
    )
}
