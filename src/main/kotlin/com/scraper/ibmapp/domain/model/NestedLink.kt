package com.scraper.ibmapp.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime

@Table("nested_link")
data class NestedLink (
    @Id
    val id: Long?,
    
    val content: String,
    
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    
    @LastModifiedDate
    val updatedAt: OffsetDateTime = OffsetDateTime.now()
)