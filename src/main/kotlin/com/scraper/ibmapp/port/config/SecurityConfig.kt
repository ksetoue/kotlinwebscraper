package com.scraper.ibmapp.port.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
class SecurityConfigLocal : WebSecurityConfigurerAdapter() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun configure(http: HttpSecurity) {
        logger.warn("SECURITY DISABLED - local PROFILE SELECTED")
        http.authorizeRequests().anyRequest().permitAll().and().cors(withDefaults()).csrf().disable()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? = UrlBasedCorsConfigurationSource().apply {
        registerCorsConfiguration("/**", CorsConfiguration().apply {
            addAllowedOrigin("*")
            addAllowedMethod("*")
            addAllowedHeader("*")
        })
    }
}

