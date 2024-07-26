package com.study.fundingkotlin.service


import com.study.fundingkotlin.entity.Funding
import com.study.fundingkotlin.entity.Member
import com.study.fundingkotlin.entity.Product
import com.study.fundingkotlin.exception.entity.prodcut.ProductNotFoundException
import com.study.fundingkotlin.repository.ProductRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import com.study.fundingkotlin.data.FundingStatus
import com.study.fundingkotlin.data.ProductResponse
import org.springframework.data.domain.*
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class ProductServiceTest {
    @InjectMocks
    lateinit var productService: ProductService

    @Mock
    lateinit var productRepository: ProductRepository

    private fun makeMember(memberId: Long) = Member(
        memberId = memberId,
        memberName = "jung"
    )

    private fun makeProduct(productId: Long) = Product(
        productId = productId,
        productName = "제품명",
        totalFundingPrice = 1000L,
        targetFundingPrice = 10000L,
        fundingStatus = "펀딩중",
        startDate = LocalDate.now(),
        finishDate = LocalDate.now().plusDays(3),
        fundingMemberNumber = 1
    )
    private fun makeFunding(fundingId: Long) = Funding(
        fundingId = fundingId,
        fundingPrice = 1000L,
        fundingDate = LocalDate.now(),
        member = makeMember(1L),
        product = makeProduct(1L)
    )

    @Test
    @DisplayName("제품 조회 - 정상")
    fun product_success(){
        val productId = 1L

        Mockito.`when`(
            productRepository.findByProductId(productId)
        ).thenReturn(
            Optional.ofNullable(makeProduct(productId))
        )

        val product: Product = productService.getProduct(productId)

        assertThat(product.productId).isEqualTo(productId)
    }

    @Test
    @DisplayName("제품 조회 - 실패")
    fun product_fail(){
        val productId = 1L

        Mockito.`when`(
            productRepository.findByProductId(productId)
        ).thenReturn(
            Optional.empty()
        )
        assertThrows<ProductNotFoundException> {
            productService.getProduct(1L)
        }
    }

    @Test
    @DisplayName("펀딩 제품 모두 조회 - 정상")
    fun productList() {
        val productId = 1L

        Mockito.`when`(
            productRepository.findAll()
        ).thenReturn(
            java.util.List.of(makeProduct(productId))
        )

        val productList: List<Product> = productService.getAllProduct()

        assertThat(productList[0].productId).isEqualTo(productId)
        assertThat(productList.size).isEqualTo(1)
    }

    @DisplayName("제품 업데이트 - 정상")
    @Test
    fun updateProduct() {
        val productId = 1L
        val product: Product = makeProduct(productId)

        Mockito.`when`(
            productRepository.findByProductId(productId)
        ).thenReturn(
            Optional.ofNullable(product)
        )

        assertThat(product.fundingMemberNumber).isEqualTo(1)

        productService.updateProduct(product)

        assertThat(product.fundingMemberNumber).isEqualTo(2)
    }

    @Test
    @DisplayName("펀딩 제품 모두 조회(list - entity 대신 반환 객체) - 성공")
    fun products(){
        val productId = 1L

        Mockito.`when`(
            productRepository.findAll()
        ).thenReturn(
            listOf(makeProduct(productId))
        )

        val products: List<ProductResponse> = productService.getProducts()
        assertThat(products.size).isEqualTo(1)
    }

    @Test
    @DisplayName("펀딩 제품 반환 객체로 모두 조회(page) - 성공")
    fun productProgress(){
        val productId = 1L
        val size = 0
        val page = 5
        val now = LocalDate.now()
        val fundingStatus: String = FundingStatus.IN_PROGRESS.status

        Mockito.`when`(
            productRepository.findProductByFundingStatusAndStartDateLessThanEqualAndFinishDateGreaterThanEqual(
                fundingStatus, now, now, PageRequest.of(size, page)
            )
        ).thenReturn(
            PageImpl(listOf(makeProduct(productId)))
        )

        val productProgress: Page<ProductResponse> = productService.getProductProgress(size, page)

        assertThat(productProgress.totalElements).isEqualTo(1)
        assertThat(productProgress.totalPages).isEqualTo(1)
    }

    @Test
    @DisplayName("펀딩 제품 반환 객체로 모두 조회(slice) - 성공")
    fun allProducts(){
        val productId = 1L
        val fundingStatus: String = FundingStatus.IN_PROGRESS.status
        val pageable: Pageable = PageRequest.of(0, 5)

        Mockito.`when`(
            productRepository.findSliceByFundingStatus(fundingStatus, pageable)
        ).thenReturn(
            SliceImpl(listOf(makeProduct(productId)))
        )

        val allProducts: Slice<ProductResponse> = productService.getAllProducts(pageable)

       assertThat(allProducts.isFirst).isTrue()
        assertThat(allProducts.isLast).isTrue()
    }
}