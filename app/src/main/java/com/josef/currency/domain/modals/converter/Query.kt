package com.josef.currency.domain.modals.converter

data class Query(
    val amount: Int,
    val from: String,
    val to: String
)