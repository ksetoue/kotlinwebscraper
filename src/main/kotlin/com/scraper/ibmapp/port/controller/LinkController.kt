package com.scraper.ibmapp.port.controller

import com.scraper.ibmapp.application.LinkApplicationService
import com.scraper.ibmapp.domain.dto.LinkDTO
import com.scraper.ibmapp.domain.model.Link
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/link")
class LinkController(
    private val linkApplicationService: LinkApplicationService
) {
    @PutMapping("/{linkId}")
    fun put(@PathVariable linkId: Long, @RequestBody linkContent: LinkDTO): ResponseEntity<Link> {
        return ResponseEntity(linkApplicationService.update(linkId, linkContent), HttpStatus.OK)
    }

    @PostMapping
    fun post(@RequestBody linkContent: LinkDTO): ResponseEntity<Link> {
        return ResponseEntity(linkApplicationService.create(linkContent), HttpStatus.CREATED)
    }

    @GetMapping
    fun getAll(): ResponseEntity<List<Link>> {
        return ResponseEntity(linkApplicationService.getAll(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Link> {
        return ResponseEntity(linkApplicationService.getById(id), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<String> {
        linkApplicationService.delete(id)
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping
    fun deleteAll(): ResponseEntity<String> {
        linkApplicationService.deleteAll()
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/find")
    fun findByTitle(@RequestParam title: String): ResponseEntity<List<Link>> {
        val foundLinks = linkApplicationService.findByTitle(title)
        return ResponseEntity(foundLinks, HttpStatus.OK)
    }
}