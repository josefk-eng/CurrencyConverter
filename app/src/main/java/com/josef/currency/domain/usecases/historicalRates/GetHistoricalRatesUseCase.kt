package com.josef.currency.domain.usecases.historicalRates

import com.josef.currency.domain.repository.CurrencyRepository

class GetHistoricalRatesUseCase(private val repository: CurrencyRepository) {
    suspend operator fun invoke(start_date:String, end_date:String, result:(Map<String, List<Pair<String,Double>>>,Exception?)->Unit){
        val res = repository.getHistoricalRates(start_date, end_date)
        if (res.error==null){
            result(res.response as Map<String, List<Pair<String,Double>>>,null)
        }else{
            result(emptyMap(),res.error)
        }
    }
}