package com.josef.currency.domain.repository

import com.josef.currency.domain.modals.wrappers.ResponseWrapper
import retrofit2.http.Query

interface CurrencyRepository {
    suspend fun getSymbols(): ResponseWrapper
    suspend fun convert(
        amount:String,
        from:String,
        to:String,
        date:String
    ):ResponseWrapper
    suspend fun getHistoricalRates(start_date:String, end_date:String): ResponseWrapper
}