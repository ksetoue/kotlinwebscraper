package com.scraper.ibmapp.domain.model.common


open class BusinessException(
    override val message: String,
    val code: String = "business-exception"
) : RuntimeException("BusinessException: $message")