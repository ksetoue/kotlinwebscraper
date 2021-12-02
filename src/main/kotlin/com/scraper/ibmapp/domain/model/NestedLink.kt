package com.scraper.ibmapp.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.*

@Table("nested_link")
data class NestedLink (
    @Id
    val id: Long?,
    
    val content: String,
    
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    
    @LastModifiedDate
    val updatedAt: OffsetDateTime = OffsetDateTime.now()
) : AbstractAggregateRoot<NestedLink>() {
    override fun equals(other: Any?): Boolean {
        return (other is NestedLink)
            && other.content == content
    }

    override fun hashCode(): Int {
        return Objects.hash(content)
    }
}