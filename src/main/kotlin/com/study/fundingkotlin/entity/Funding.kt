package com.study.fundingkotlin.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate

@Entity
@Table(name = "funding")
@EntityListeners(AuditingEntityListener::class)
data class Funding(
    @Id
    @Column(name = "funding_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val fundingId: Long? = null,

    @Column(name = "funding_price", nullable = false)
    val fundingPrice: Long,

    @CreatedDate
    @Column(name = "funding_date")
    val fundingDate: LocalDate? = null,

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: Product,

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member
)
