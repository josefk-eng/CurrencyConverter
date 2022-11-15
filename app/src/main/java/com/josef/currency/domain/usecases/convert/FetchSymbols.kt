package com.josef.currency.domain.usecases.convert

import com.josef.currency.domain.repository.CurrencyRepository

class FetchSymbols(private val repository:CurrencyRepository) {
    suspend operator fun invoke(result:(Map<String, String>, e:Exception?)->Unit){
        val response = repository.getSymbols()
        response.error?.let {
            result(emptyMap(),it)
        }
        response.response?.let {
            try {
                result(it as Map<String, String>, null)
            }catch (e:Exception){
                result(emptyMap(), e)
            }

        }
    }
}