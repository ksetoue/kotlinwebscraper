package com.scraper.ibmapp.port.client

import com.scraper.ibmapp.domain.model.NestedLink
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.eachHref
import it.skrape.selects.html5.a
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class SkrapeData {
    fun search(links: List<String>): MutableList<NestedLink> {
        var allLinks = listOf<String>()

        val nestedLinks = mutableListOf<NestedLink>()

        runBlocking {
            val deferred = links.map { link  ->
                async {
                    scrape(link)
                }
            }

            allLinks = deferred.awaitAll().flatten()
        }

        allLinks.forEach { nestedLinks.add(NestedLink(id = null, content = it)) }

        return nestedLinks
    }

    private fun scrape(urlToScrap: String) =
        skrape(HttpFetcher) {
            request {
                url = urlToScrap
            }.also { println("call ${it.preparedRequest.url} at ${OffsetDateTime.now()}") }

            response {
                htmlDocument {
                    a {
                        findAll {
                            eachHref
                        }
                    }
                }
            }
        }
}