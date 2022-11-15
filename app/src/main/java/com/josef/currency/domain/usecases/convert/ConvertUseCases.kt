package com.josef.currency.domain.usecases.convert

data class ConvertUseCases(
    val fetchSymbols: FetchSymbols,
    val convert: Convert
)