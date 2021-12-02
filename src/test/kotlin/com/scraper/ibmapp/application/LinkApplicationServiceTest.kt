package com.scraper.ibmapp.application

import com.scraper.ibmapp.AbstractBaseTest
import com.scraper.ibmapp.domain.dto.LinkDTO
import com.scraper.ibmapp.domain.model.Link
import com.scraper.ibmapp.domain.model.LinkRepository
import com.scraper.ibmapp.domain.model.NestedLink
import com.scraper.ibmapp.domain.model.common.ResourceNotFoundException
import com.scraper.ibmapp.port.client.ScrapeDataClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify
import java.util.*

class LinkApplicationServiceTest(
    @Mock private val linkRepositoryMock: LinkRepository,
    @Mock private val scraperMock: ScrapeDataClient
) : AbstractBaseTest() {

    private val linkApplicationService = LinkApplicationService(linkRepositoryMock, scraperMock)

    @Test
    fun `#getById must throw an resourceNotFoundException when an id does not exists`() {
        val id = 1L

        `when`(linkRepositoryMock.findById(id)).thenReturn(Optional.empty())

        assertThrows<ResourceNotFoundException> {
            linkApplicationService.getById(id)
        }
    }

    @Test
    fun `#create must insert a new link when nothing goes wrong`() {
        val linkDTO = buildFixture<LinkDTO>("default")
        val linksToScrap = listOf(linkDTO.source)
        val linkFixture = buildFixture<Link>("default")
        val nestedLinks = mutableListOf(buildFixture<NestedLink>("default"))

        val expectedLink = linkFixture.copy(nestedLinks = nestedLinks.toSet())

        `when`(scraperMock.search(linksToScrap)).thenReturn(nestedLinks)
        `when`(linkRepositoryMock.save(any(Link::class.java))).thenReturn(expectedLink)

        val returnedValue = linkApplicationService.create(linkDTO)

        assertThat(returnedValue.nestedLinks.size).isGreaterThan(0)
        assertThat(returnedValue.title).contains(expectedLink.title)
        assertThat(returnedValue.source).contains(expectedLink.source)

        verify(linkRepositoryMock).save(any(Link::class.java))
    }

    @Test
    fun `#update must throw an resourceNotFoundException when an linkId does not exists`() {
        val id = 1L
        val linkDTO = buildFixture<LinkDTO>("default")

        `when`(linkRepositoryMock.findById(id)).thenReturn(Optional.empty())

        assertThrows<ResourceNotFoundException> {
            linkApplicationService.update(id, linkDTO)
        }
    }

    @Test
    fun `#update must update an record when an linkId exists`() {
        val linkToUpdate = buildFixture<Link>("default")
        val linkDTO = buildFixture<LinkDTO>("default")
            .copy(title = linkToUpdate.title, source = linkToUpdate.source)

        val source = listOf(linkDTO.source)
        val id = linkToUpdate.id

        val newTitle = "new"
        val expectedLink = linkToUpdate.copy(
            id = linkToUpdate.id,
            title = newTitle,
            source = linkToUpdate.source,
            createdAt = linkToUpdate.createdAt)

        `when`(linkRepositoryMock.findById(id!!)).thenReturn(Optional.of(linkToUpdate))
        `when`(scraperMock.search(source)).thenReturn(mutableListOf())
        `when`(linkRepositoryMock.save(any(Link::class.java))).thenReturn(expectedLink)

        val returned = linkApplicationService.update(id, linkDTO)

        assertThat(returned.source).contains(expectedLink.source)
        assertThat(returned.title).contains(expectedLink.title)
        assertThat(returned.id).isEqualTo(expectedLink.id)

        verify(linkRepositoryMock).save(any(Link::class.java))
    }
}