package com.scraper.ibmapp.domain.dto

import com.scraper.ibmapp.domain.model.Link

data class LinkDTO(
    val title: String,
    val source: String
) {
    fun toLink(): Link = Link(
        id = null,
        title = title,
        source = source
    )
}