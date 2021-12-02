package com.scraper.ibmapp.template

import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.Rule
import br.com.six2six.fixturefactory.loader.TemplateLoader
import com.scraper.ibmapp.domain.model.Link
import com.scraper.ibmapp.domain.model.NestedLink
import java.time.OffsetDateTime

class LinkTemplate : TemplateLoader {
    override fun load() {
        loadLinks()
        loadNested()
    }

    private fun loadLinks() {
        Fixture.of(Link::class.java)
            .addTemplate("default", Rule().apply {
                add("id", uniqueRandom(*(1L..999L).toList().toTypedArray()))
                add("title", regex("\\w{10}"))
                add("source", "https://www.google.com")
                add("createdAt", OffsetDateTime.now())
                add("nestedLinks", emptyList<NestedLink>())
            })
    }

    private fun loadNested() {
        Fixture.of(NestedLink::class.java)
            .addTemplate("default", Rule().apply {
                add("id", uniqueRandom(*(1L..999L).toList().toTypedArray()))
                add("content", regex("\\w{10}"))
                add("createdAt", OffsetDateTime.now())
                add("updatedAt", OffsetDateTime.now())
            })
    }

}