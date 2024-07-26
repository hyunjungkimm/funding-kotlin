package com.study.fundingkotlin.repository


import com.study.fundingkotlin.data.FundingStatus
import com.study.fundingkotlin.entity.Product
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import java.time.LocalDate
import java.util.*

@DataJpaTest
internal class ProductRepositoryTest {
    @Autowired
    lateinit var productRepository: ProductRepository

    @Test
    fun findByProductId() {
        val productId = 1L
        val product: Optional<Product> = productRepository.findByProductId(productId)

        assertThat(product.get().productId).isEqualTo(1L)
    }

    @Test
    fun findProductByFundingStatusAndStartDateLessThanEqualAndFinishDateGreaterThanEqual() {
        val productPage: Page<Product> =
            productRepository.findProductByFundingStatusAndStartDateLessThanEqualAndFinishDateGreaterThanEqual(
                FundingStatus.IN_PROGRESS.status, LocalDate.now(), LocalDate.now(), PageRequest.of(0, 5)
            )

        assertThat(productPage.totalPages).isEqualTo(1L)
        assertThat(productPage.totalElements).isEqualTo(2L)
    }

    @Test
    fun findSliceByFundingStatus() {
        val sliceByFundingStatus: Slice<Product> =
            productRepository.findSliceByFundingStatus(FundingStatus.IN_PROGRESS.status, PageRequest.of(0, 1))

        assertThat(sliceByFundingStatus.isFirst).isTrue()
        assertThat(sliceByFundingStatus.isLast).isFalse()
    }
}