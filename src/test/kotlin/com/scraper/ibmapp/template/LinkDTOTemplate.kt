package com.scraper.ibmapp.template

import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.Rule
import br.com.six2six.fixturefactory.loader.TemplateLoader
import com.scraper.ibmapp.domain.dto.LinkDTO

class LinkDTOTemplate : TemplateLoader {
    override fun load() {
        Fixture.of(LinkDTO::class.java)
            .addTemplate("default", Rule().apply {
                add("title", regex("\\w{10}"))
                add("source", "https://www.google.com")
            })
    }
}