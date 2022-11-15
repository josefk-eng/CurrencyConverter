package com.josef.currency.domain.usecases.convert

import com.josef.currency.domain.modals.wrappers.ResponseWrapper
import com.josef.currency.domain.repository.CurrencyRepository
import retrofit2.http.Query

class Convert(private val repository: CurrencyRepository) {
    suspend operator fun invoke(
        amount:String,
        from:String,
        to:String,
        date:String
    ):ResponseWrapper{
        return repository.convert(amount, from, to, date)
    }
}