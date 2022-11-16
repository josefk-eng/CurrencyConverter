package com.josef.currency.domain.hilt

import com.josef.currency.domain.repository.CurrencyRepository
import com.josef.currency.domain.usecases.convert.Convert
import com.josef.currency.domain.usecases.convert.ConvertUseCases
import com.josef.currency.domain.usecases.convert.FetchSymbols
import com.josef.currency.domain.usecases.historicalRates.GetHistoricalRatesUseCase
import com.josef.currency.domain.usecases.historicalRates.HistoricalUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {

    @Provides
    @Singleton
    fun providesConvertUseCases(repository: CurrencyRepository):ConvertUseCases{
        return ConvertUseCases(
            fetchSymbols = FetchSymbols(repository),
            convert = Convert(repository)
        )
    }

    @Provides
    @Singleton
    fun providesHistoricalUseCases(repository: CurrencyRepository):HistoricalUseCase{
        return HistoricalUseCase(
            getHistoricalRatesUseCase = GetHistoricalRatesUseCase(repository)
        )
    }
}