package com.scraper.ibmapp.port.controller

import com.scraper.ibmapp.AbstractBaseTest
import com.scraper.ibmapp.application.LinkApplicationService
import com.scraper.ibmapp.domain.dto.LinkDTO
import com.scraper.ibmapp.domain.model.Link
import com.scraper.ibmapp.domain.model.NestedLink
import com.scraper.ibmapp.port.client.ScrapeDataClient
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@WebMvcTest(LinkController::class)
@AutoConfigureMockMvc(addFilters = false)
class LinkControllerTest(
    @Autowired
    val mockMvc: MockMvc
) : AbstractBaseTest() {
    @MockBean
    private lateinit var linkApplicationServiceMock: LinkApplicationService
    
    @Test
    fun `PUT must return 200 when nothing goes wrong`() {
        val title = "google.com"
        val source = "https://google.com"
        val linkDTO = buildFixture<LinkDTO>("default")
        val newLink = buildFixture<Link>("default").copy(source = source, title = title)

        val updatedLink = newLink.copy(title = linkDTO.title, source = linkDTO.source)

        `when`(linkApplicationServiceMock.update(newLink.id!!, linkDTO)).thenReturn(updatedLink)

        val content = "{\"title\": \"$title\", \"source\":\"$source\"}"

        mockMvc.perform(put("/link/${newLink.id}")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
    
    @Test
    fun `POST must return 204 when a new link is created`() {
        val title = "ibm.com"
        val source = "https://ibm.com"
        val newLink = buildFixture<Link>("default").copy(source = source, title = title)

        `when`(linkApplicationServiceMock.create(any(LinkDTO::class.java))).thenReturn(newLink)

        val content = "{\"title\": \"$title\", \"source\":\"$source\"}"

        mockMvc.perform(post("/link")
            .content(content)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }
    
    @Test
    fun `GET all must return a list of links when nothing goes wrong`() {
        val returnedLink = buildFixture<Link>("default")
        `when`(linkApplicationServiceMock.getAll()).thenReturn(listOf(returnedLink))

        mockMvc
            .perform(get("/link"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
    }
    
    @Test
    fun `GET by id must return the correct link`() {
        val returnedLink = buildFixture<Link>("default")
        `when`(linkApplicationServiceMock.getById(returnedLink.id!!)).thenReturn(returnedLink)

        mockMvc
            .perform(get("/link/${returnedLink.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(returnedLink.id))
    }

}