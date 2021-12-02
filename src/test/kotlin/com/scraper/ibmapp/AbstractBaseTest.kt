package com.scraper.ibmapp

import br.com.six2six.fixturefactory.Fixture
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@ExtendWith(MockitoExtension::class)
abstract class AbstractBaseTest {

    init {
        FixtureFactoryLoader.loadTemplates("com.scraper.ibmapp.template");
    }

    inline fun <reified T : Any> buildFixture(label: String): T {
        return Fixture.from(T::class.java).gimme(label)
    }

    inline fun <reified T : Any> buildFixture(quantity: Int, label: String): List<T> {
        return Fixture.from(T::class.java).gimme(quantity, label)
    }

    inline fun <reified T : Any> buildFixture(quantity: Int, label: List<String>): List<T> {
        return Fixture.from(T::class.java).gimme(quantity, label)
    }

    fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    val objectMapper = ObjectMapper().apply {
        propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        registerModule(JavaTimeModule())
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}