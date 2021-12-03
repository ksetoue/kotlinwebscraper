package com.scraper.ibmapp.application

import com.scraper.ibmapp.domain.dto.LinkDTO
import com.scraper.ibmapp.domain.model.Link
import com.scraper.ibmapp.domain.model.LinkRepository
import com.scraper.ibmapp.domain.model.common.ResourceNotFoundException
import com.scraper.ibmapp.port.client.ScrapeDataClient
import org.slf4j.LoggerFactory
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.regex
import org.springframework.stereotype.Service

@Service
class LinkApplicationService(
    private val linkRepository: LinkRepository,
    private val scraper: ScrapeDataClient
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    
    fun update(linkId: Long, linkDTO: LinkDTO): Link {
        logger.info("updating linkId: $linkId")
        val validUrl = validUrl(linkDTO.source)

        return linkRepository.findById(linkId)
            .map {
                val newLinksFound = scraper.search(listOf(validUrl)).toSet()

                val linkToUpdate = it.copy(
                    title = linkDTO.title,
                    source = linkDTO.source,
                    nestedLinks = newLinksFound
                )

                linkRepository.save(linkToUpdate)
            }
            .orElseThrow { ResourceNotFoundException("LinkId $linkId not found") }
    }

    fun create(linkContent: LinkDTO): Link {
        logger.info("scraping data")
        val validUrl = validUrl(linkContent.source)

        val links = scraper.search(listOf(validUrl)).toSet()

        logger.info("creating a new link content")
        val newLink = Link(
            id = null,
            title = linkContent.title,
            source = linkContent.source
        )

        return linkRepository.save(newLink.copy(nestedLinks = links))
    }

    fun getAll(): List<Link> {
        val allSourceLinks = linkRepository.findAll()

        val clearLinks = allSourceLinks.map { link ->
            val nestedLinks = link.nestedLinks.map {
                if (it.content.contains("http")) {
                    it
                } else {
                    it.copy(content = link.source + it.content)
                }
            }

            link.copy(nestedLinks = nestedLinks.toSet())
        }

        return clearLinks
    }

    fun getById(id: Long): Link? {
        return linkRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Link") }
    }

    fun delete(id: Long) {
        linkRepository.deleteById(id)
    }

    fun deleteAll() {
        linkRepository.deleteAll()
    }

    fun findByTitle(title: String): List<Link> {
        val titleToFind = "%$title%"
        return linkRepository.findByTitle(titleToFind)
    }

    private fun validUrl(url: String): String {
        val hasHttp = url.matches("^(http|https)://".toRegex())
        return if (hasHttp) {
            url
        } else {
            "http://$url"
        }
    }
}