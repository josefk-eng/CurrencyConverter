package com.josef.currency.domain.usecases.convert

import com.josef.currency.domain.currentDate
import com.josef.currency.domain.modals.converter.ConvertResponse
import com.josef.currency.domain.modals.wrappers.ResponseWrapper
import com.josef.currency.domain.repository.CurrencyRepository
import retrofit2.http.Query

class Convert(private val repository: CurrencyRepository) {
    suspend operator fun invoke(
        amount:String,
        from:String,
        to:String,
        result: (ConvertResponse?, Exception?)->Unit
    ){
        val response = repository.convert(amount, from, to, currentDate("YYYY-MM-DD"))
        response.error?.let {
            result(null,it)
        }
        response.response?.let {
            try {
                val converted = it as ConvertResponse
                result(converted, null)
            }catch (e:Exception){
                result(null, e)
            }
        }
    }
}