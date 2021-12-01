package com.scraper.ibmapp.domain.model

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LinkRepository : CrudRepository<Link, Long>