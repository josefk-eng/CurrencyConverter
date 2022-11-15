package com.josef.currency.data.source.remote

import com.josef.currency.domain.modals.converter.ConvertResponse
import com.josef.currency.domain.modals.wrappers.ResponseWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteInterface {

    @GET("symbols")
    suspend fun getSymbols():String

    @GET("convert")
    suspend fun convert(
        @Query("amount") amount:String,
        @Query("from") from:String,
        @Query("to") to:String,
        @Query("date") date:String
    ):ConvertResponse
}