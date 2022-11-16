package com.josef.currency.domain.modals.historicalData

import com.josef.currency.presentation.historicalRates.ListAdapterItem

data class HistoricalData(
    val date:String,
    val rates:List<Pair<String,Double>>,
):ListAdapterItem {
    override val id: String
        get() = date
}