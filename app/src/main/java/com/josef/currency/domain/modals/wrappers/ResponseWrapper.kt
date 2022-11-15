package com.josef.currency.domain.modals.wrappers


data class ResponseWrapper(
    val response:Any? = null,
    val error: Exception? = null
)