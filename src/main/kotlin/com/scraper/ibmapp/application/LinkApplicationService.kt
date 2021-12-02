package com.scraper.ibmapp.application

import com.scraper.ibmapp.domain.dto.LinkDTO
import com.scraper.ibmapp.domain.model.Link
import com.scraper.ibmapp.domain.model.LinkRepository
import com.scraper.ibmapp.domain.model.common.ResourceNotFoundException
import com.scraper.ibmapp.port.client.ScrapeDataClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LinkApplicationService(
    private val linkRepository: LinkRepository,
    private val scraper: ScrapeDataClient
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    
    fun update(linkId: Long, linkDTO: LinkDTO): Link {
        logger.info("updating linkId: $linkId")

        return linkRepository.findById(linkId)
            .map {
                val linkToUpdate = it.copy(
                    title = linkDTO.title,
                    source = linkDTO.source
                )

                linkRepository.save(linkToUpdate)
            }
            .orElseThrow { ResourceNotFoundException("LinkId $linkId not found") }
    }

    fun create(linkContent: LinkDTO): Link {
        logger.info("scraping data")
        val links = scraper.search(listOf(linkContent.source)).toSet()

        logger.info("creating a new link content")
        val newLink = Link(
            id = null,
            title = linkContent.title,
            source = linkContent.source
        )

        return linkRepository.save(newLink.copy(nestedLinks = links))
    }

    fun getAll(): MutableIterable<Link> {
        return linkRepository.findAll()
    }

    fun getById(id: Long): Link? {
        return linkRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Link") }
    }
}