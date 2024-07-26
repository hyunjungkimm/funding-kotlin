package com.study.fundingkotlin.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import com.study.fundingkotlin.data.FundingStatus
import com.study.fundingkotlin.data.ProductResponse
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@Entity
@Table(name= "product")
@EntityListeners(AuditingEntityListener::class)
data class Product(
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var productId : Long? = null,

    @Column(name = "product_name", nullable = false)
    var productName : String,

    @Column(name = "target_funding_price", nullable = false)
    var targetFundingPrice : Long,

    @Column(name = "total_funding_price", nullable = false)
    var totalFundingPrice : Long,

    @Column(name = "funding_status", nullable = false, length = 30)
    var fundingStatus: String,

    @Column(name = "start_date", nullable = false)
    @CreatedDate
    var startDate : LocalDate,

    @Column(name = "finish_date", nullable = false)
    var finishDate : LocalDate,

    @Column(name = "funding_member_number", nullable = false)
    var fundingMemberNumber: Int = 0,

    @JsonManagedReference
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    var fundingList: List<Funding> = ArrayList()

    //@Version
    //var version;
)
