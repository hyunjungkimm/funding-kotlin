package com.study.fundingkotlin.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "member")
@EntityListeners(AuditingEntityListener::class)
data class Member(
    @Id
    @Column(name = "member_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId : Long? = null,
    @Column(name = "member_name", nullable = false)
    val memberName : String,
    @Column(name = "created_at")
    @CreatedDate
    val createdAt : LocalDateTime,
    @JsonManagedReference
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    val fundingList : List<Funding> = ArrayList()
)
