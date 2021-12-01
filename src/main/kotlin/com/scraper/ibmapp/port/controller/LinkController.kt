package com.scraper.ibmapp.port.controller

import com.scraper.ibmapp.application.LinkApplicationService
import com.scraper.ibmapp.domain.model.Link
import com.scraper.ibmapp.domain.dto.LinkCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/link")
class LinkController(
    private val linkApplicationService: LinkApplicationService
) {
    @PutMapping("/{linkId}")
    fun put(@PathVariable linkId: Long, @RequestBody linkContent: LinkCommand): ResponseEntity<Link> {
        return ResponseEntity(linkApplicationService.update(linkId, linkContent), HttpStatus.OK)
    }

    @PostMapping
    fun post(@RequestBody linkContent: LinkCommand): ResponseEntity<Link> {
        return ResponseEntity(linkApplicationService.create(linkContent), HttpStatus.CREATED)
    }
}