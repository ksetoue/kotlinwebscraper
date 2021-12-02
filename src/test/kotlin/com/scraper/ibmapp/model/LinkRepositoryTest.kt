package com.scraper.ibmapp.model

import com.scraper.ibmapp.AbstractRepositoryTest
import com.scraper.ibmapp.domain.model.LinkRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class LinkRepositoryTest(
    @Autowired private val linkRepository: LinkRepository
) : AbstractRepositoryTest() {
    @BeforeEach
    fun setup() {
        linkRepository.deleteAll()
    }

}