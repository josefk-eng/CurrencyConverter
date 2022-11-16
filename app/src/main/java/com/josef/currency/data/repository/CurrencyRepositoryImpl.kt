package com.josef.currency.data.repository

import com.josef.currency.core.utils.toMap
import com.josef.currency.data.source.remote.RemoteInterface
import com.josef.currency.domain.modals.wrappers.ResponseWrapper
import com.josef.currency.domain.repository.CurrencyRepository
import org.json.JSONObject
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val remote:RemoteInterface): CurrencyRepository {
    override suspend fun getSymbols(): ResponseWrapper {
        val map: Map<String,Any?>?
        return  try {
            val response = remote.getSymbols()
            map = JSONObject(response).toMap()
            if (map["success"] as Boolean){
                ResponseWrapper(response = map["symbols"])
            }else{
                ResponseWrapper(error = Exception("Request was not Successful"))
            }
        }catch (e:Exception){
            ResponseWrapper(error = e)
        }
    }

    override suspend fun convert(
        amount: String,
        from: String,
        to: String,
        date: String
    ): ResponseWrapper {
        return try {
            val response = remote.convert(amount, from, to, date)
            if (response.success){
                ResponseWrapper(response = response, error = null)
            }else{
                ResponseWrapper(response = null, error = Exception("Could not convert amount"))
            }
        }catch (e:Exception){
            ResponseWrapper(error = e)
        }
    }

    override suspend fun getHistoricalRates(start_date:String, end_date:String):ResponseWrapper {
        val map: Map<String,Any?>?
        return  try {
            val response = remote.getHistoricalRates(start_date, end_date)
            map = JSONObject(response).toMap()
            if (map["success"] as Boolean){
                ResponseWrapper(response = map["rates"])
            }else{
                ResponseWrapper(error = Exception("Request was not Successful"))
            }
        }catch (e:Exception){
            ResponseWrapper(error = e)
        }
    }
}