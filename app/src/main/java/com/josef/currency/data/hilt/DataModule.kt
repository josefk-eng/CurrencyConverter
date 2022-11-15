package com.josef.currency.data.hilt

import com.josef.currency.core.utils.Constants
import com.josef.currency.data.repository.CurrencyRepositoryImpl
import com.josef.currency.data.source.remote.HeaderInterceptor
import com.josef.currency.data.source.remote.RemoteInterface
import com.josef.currency.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun providesRemoteInterface(): RemoteInterface {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(RemoteInterface::class.java)
    }

    @Provides
    @Singleton
    fun providesRepository(remote:RemoteInterface): CurrencyRepository = CurrencyRepositoryImpl(remote)
}