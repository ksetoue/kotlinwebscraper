package com.scraper.ibmapp.domain.model.common

class ResourceNotFoundException(entity: String, code: String = "resource-not-found")
    : BusinessException("$entity not found", code)