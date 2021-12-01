package com.scraper.ibmapp.domain.model

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NestedLinkRepository : CrudRepository<NestedLink, Long> {

}