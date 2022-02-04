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

@Service
class ItemApplicationService(
    private val itemRepository: ItemRepository,
    private val client: ItemDataClient
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun update(itemId: Long, itemDTO: ItemDTO): Item {
        logger.info("updating itemId: $itemId")

        return itemRepository.findById(itemId)
            .map {
                val itemToUpdate = it.copy(
                    title = itemDTO.title,
                    source = itemDTO.source
                )

                itemRepository.save(linkToUpdate)
            }
            .orElse {
                logger.info("erro")
            }
    }

    fun create(itemContent: ItemDTO): Link {
        logger.info("creating a new item")

        val newItem = Item(
            id = null,
            title = linkContent.title,
            source = itemContent.source
        )

        return itemRepository.save(newItem)
    }

    fun getAll(): List<Item> {
        val allSourceLinks = itemRepository.findAll()

        return clearLinks
    }

    fun getById(id: Long): Item? {
        return itemRepository.findById(id)
    }

    fun delete(id: Long) {
        itemRepository.deleteById(id)
    }

    fun deleteAll() {
        itemRepository.deleteAll()
    }

    fun findByTitle(title: String): List<Item> {
        val titleToFind = "%$title%"
        return itemRepository.findByTitle(titleToFind)
    }
}