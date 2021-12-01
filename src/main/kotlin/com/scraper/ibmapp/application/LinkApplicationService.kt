package com.scraper.ibmapp.application

import com.scraper.ibmapp.domain.dto.LinkCommand
import com.scraper.ibmapp.domain.model.Link
import com.scraper.ibmapp.domain.model.LinkRepository
import com.scraper.ibmapp.domain.model.NestedLinkRepository
import com.scraper.ibmapp.domain.model.common.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LinkApplicationService(
    private val linkRepository: LinkRepository,
    private val nestedLinkRepository: NestedLinkRepository
) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    
    fun update(linkId: Long, linkCommand: LinkCommand): Link {
        logger.info("updating linkId: $linkId")

        return linkRepository.findById(linkId)
            .map {
                val linkToUpdate = it.copy(
                    title = linkCommand.title,
                    source = linkCommand.source
                )

                linkRepository.save(linkToUpdate)
            }
            .orElseThrow { ResourceNotFoundException("LinkId $linkId not found") }
    }

    fun create(linkContent: LinkCommand): Link {
        logger.info("creating a new link content")
        val newLink = Link(
            id = null,
            title = linkContent.title,
            source = linkContent.source
        )

        return linkRepository.save(newLink)
    }
}