package com.study.fundingkotlin.service

import com.study.fundingkotlin.data.FundingStatus
import com.study.fundingkotlin.data.ProductFundingResponse
import com.study.fundingkotlin.data.ProductResponse
import com.study.fundingkotlin.entity.Product
import com.study.fundingkotlin.error.ErrorCode
import com.study.fundingkotlin.exception.entity.prodcut.ProductNotFoundException
import com.study.fundingkotlin.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ProductService(
    val productRepository: ProductRepository
) {

    @Transactional(readOnly = true)
    fun getProduct(productId: Long): Product {
        return productRepository.findByProductId(productId)
            .orElseThrow { ProductNotFoundException(ErrorCode.NOT_FOUND_PRODUCT) }
    }

    @Transactional
    fun updateProduct(product: Product) {
        val updateProduct: Product = productRepository.findByProductId(product.productId!!)
            .orElseThrow { ProductNotFoundException(ErrorCode.NOT_FOUND_PRODUCT) }

        updateProduct.apply {
            productId = product.productId
            productName = product.productName
            targetFundingPrice = product.targetFundingPrice
            totalFundingPrice = product.totalFundingPrice
            fundingStatus = product.fundingStatus
            startDate = product.startDate
            finishDate = product.finishDate
            fundingMemberNumber = product.fundingMemberNumber + 1
        }
    }

    @Transactional(readOnly = true)
    fun getAllProduct() :  List<Product> {
        return productRepository.findAll()
    }


    @Transactional(readOnly = true)
    fun getProducts() : List<ProductResponse> {
        return productRepository.findAll().map {product ->
            val productFundingResponseList = product.fundingList.map{ funding ->
                ProductFundingResponse(fundingId = funding.fundingId!!)
            }
            ProductResponse(
                productId = product.productId!!,
                productName = product.productName,
                targetFundingPrice = product.targetFundingPrice,
                fundingMemberNumber = product.fundingMemberNumber,
                startDate = product.startDate,
                finishDate = product.finishDate,
                fundingList = productFundingResponseList
            )
        }
    }


    @Transactional(readOnly = true)
    fun getProductProgress(size: Int, page: Int): Page<ProductResponse> {
        val pageRequest: PageRequest = PageRequest.of(size, page)
        val now = LocalDate.now()
        val products: Page<Product> =
            productRepository.findProductByFundingStatusAndStartDateLessThanEqualAndFinishDateGreaterThanEqual(
                FundingStatus.IN_PROGRESS.status, now, now, pageRequest
            )

        return products.map { product ->
            ProductResponse (
                productId = product.productId!!,
                productName = product.productName,
                targetFundingPrice = product.targetFundingPrice,
                fundingMemberNumber = product.fundingMemberNumber,
                startDate = product.startDate,
                finishDate = product.finishDate,
            )
        }
    }

    @Transactional(readOnly = true)
    fun getAllProducts(pageable: Pageable): Slice<ProductResponse> {
        val products: Slice<Product> =
            productRepository.findSliceByFundingStatus(FundingStatus.IN_PROGRESS.status, pageable)
        return products.map {product ->
            ProductResponse (
                productId = product.productId!!,
                productName = product.productName,
                targetFundingPrice = product.targetFundingPrice,
                fundingMemberNumber = product.fundingMemberNumber,
                startDate = product.startDate,
                finishDate = product.finishDate,
            )
        }
    }
}
