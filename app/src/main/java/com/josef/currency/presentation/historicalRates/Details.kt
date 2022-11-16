package com.josef.currency.presentation.historicalRates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.josef.currency.R
import com.josef.currency.databinding.FragmentDetailsBinding
import com.josef.currency.presentation.converter.ConverterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class Details : Fragment() {

    private lateinit var binding:FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: HistoricalViewModel by viewModels()
        viewModel.getHistory()

        binding = FragmentDetailsBinding.inflate(inflater,container,false)
        binding.viewModel = viewModel
        binding.adapter = HistoricalAdapter(viewModel.historicState.value.data)
        return binding.root
    }


}