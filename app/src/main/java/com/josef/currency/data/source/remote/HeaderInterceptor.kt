package com.josef.currency.data.source.remote

import com.josef.currency.core.utils.Constants.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("apikey",API_KEY)
            .build()

        return chain.proceed(request)
    }
}