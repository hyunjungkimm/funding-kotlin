package com.study.fundingkotlin.repository

import com.study.fundingkotlin.entity.Funding
import com.study.fundingkotlin.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FundingRepository : JpaRepository<Funding, Long> {

    fun findAllByMember(member : Member) : List<Funding>
}