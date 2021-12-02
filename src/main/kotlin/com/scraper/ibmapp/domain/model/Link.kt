package com.scraper.ibmapp.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.domain.AbstractAggregateRoot
import org.springframework.data.relational.core.mapping.MappedCollection
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.*

@Table("link")
data class Link(
    @Id
    val id: Long?,
    
    val title: String,
    
    val source: String,
    
    val createdAt: OffsetDateTime = OffsetDateTime.now(),
    
    @MappedCollection(idColumn = "source_id")
    var nestedLinks: Set<NestedLink> = emptySet()
) : AbstractAggregateRoot<Link>() {

    override fun equals(other: Any?): Boolean {
        return (other is Link)
            && other.title == title
            && other.source == source
    }

    override fun hashCode(): Int {
        return Objects.hash(title, source)
    }

}