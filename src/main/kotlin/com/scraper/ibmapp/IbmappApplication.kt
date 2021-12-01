package com.scraper.ibmapp

import com.scraper.ibmapp.port.client.SkrapeData
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IbmappApplication

fun main(args: Array<String>) {
	runApplication<IbmappApplication>(*args)

    SkrapeData().search("skrape.it").forEach {
        println(it)
    }
}
