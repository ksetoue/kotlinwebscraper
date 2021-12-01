package com.scraper.ibmapp.port.client

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
class SkrapeData(
    val links: List<String>
) {
//    val link = listOf("https://www.google.com", "https://www.github.com")

    fun search(partialHref: String): List<String> {
        var allLinks = listOf<String>()
        runBlocking {
            val deferred = links.map { link  ->
                async {
                    scrape(link)
                }
            }
                allLinks = deferred.awaitAll().flatten()
//                println("all links: $allLinks")
            }

        return allLinks
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