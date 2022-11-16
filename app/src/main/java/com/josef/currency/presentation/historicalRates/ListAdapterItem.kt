package com.josef.currency.presentation.historicalRates

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

interface ListAdapterItem {
    val id : String
}

class BaseViewHolder<BINDING : ViewDataBinding>(val binder: BINDING) :
    RecyclerView.ViewHolder(binder.root) {
}