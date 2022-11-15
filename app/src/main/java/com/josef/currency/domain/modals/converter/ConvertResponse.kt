package com.josef.currency.domain.modals.converter

data class ConvertResponse(
    val date: String,
    val historical: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)