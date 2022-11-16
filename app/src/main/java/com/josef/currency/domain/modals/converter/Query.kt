package com.josef.currency.domain.modals.converter

data class Query(
    val amount: Double,
    val from: String,
    val to: String
)