package com.scraper.ibmapp.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime

@Table("link")
data class Link(
    @Id
    val id: Long?,
    
    val title: String,
    
    val source: String,
    
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    
    @MappedCollection(idColumn = "source_id")
    var nestedLinks: Set<NestedLink> = emptySet()
)