package com.josef.currency.presentation.historicalRates

import com.josef.currency.R
import com.josef.currency.databinding.HistoryItemBinding
import com.josef.currency.domain.modals.historicalData.HistoricalData

class HistoricalAdapter(
    private val list: List<HistoricalData>
):BaseAdapter<HistoryItemBinding,HistoricalData>(list) {
    override val layoutId: Int = R.layout.history_item

    override fun bind(binding: HistoryItemBinding, item: HistoricalData) {
        binding.apply {
            historicalData = item
            executePendingBindings()
        }
    }
}