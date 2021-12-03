package com.scraper.ibmapp.domain.model

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LinkRepository : CrudRepository<Link, Long> {
    @Query("""
        select *
        from link
        where title like :title
    """)
    fun findByTitle(title: String): List<Link>

}