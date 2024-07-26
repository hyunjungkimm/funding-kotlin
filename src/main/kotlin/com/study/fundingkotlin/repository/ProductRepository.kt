package com.study.fundingkotlin.repository

import com.study.fundingkotlin.entity.Product
import jakarta.persistence.LockModeType
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    fun findProductByFundingStatusAndStartDateLessThanEqualAndFinishDateGreaterThanEqual(
        fundingStatus : String, start : LocalDate, finish : LocalDate, pageRequest: PageRequest
    ) : Page<Product>

    @Query("SELECT p FROM Product p WHERE p.fundingStatus =:fundingStatus AND p.startDate <= :start AND p.finishDate >= :finish")
    fun findProductByFundingStatusAndDateRange(
        @Param("fundingStatus") fundingStatus: String,
        @Param("start") start: LocalDate,
        @Param("finish") finish: LocalDate,
        pageable: Pageable
    ) : Page<Product>

    fun findSliceByFundingStatus(FundingStatus: String?, pageable: Pageable): Slice<Product>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByProductId(productId: Long): Optional<Product>
}