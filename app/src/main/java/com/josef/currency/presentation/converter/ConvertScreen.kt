package com.josef.currency.presentation.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.josef.currency.R
import com.josef.currency.core.utils.afterTextChanged
import com.josef.currency.databinding.FragmentConverterBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ConvertScreen : Fragment() {

    private lateinit var binding:FragmentConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConverterBinding.inflate(inflater,container,false)
        val viewModel:ConverterViewModel by viewModels()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.fromAmount.afterTextChanged { amount->
            viewModel.onEvent(ConverterEvent.OnFromAmountChanged(amount))
        }
        binding.toAmount.afterTextChanged { amount->
            viewModel.onEvent(ConverterEvent.OnToAmountChanged(amount))
        }
        binding.fromAmount.onFocusChangeListener = View.OnFocusChangeListener { v, focus -> viewModel.onEvent(ConverterEvent.OnFromFocusedChanged(focus)) }
        binding.toAmount.onFocusChangeListener = View.OnFocusChangeListener { v, focus -> viewModel.onEvent(ConverterEvent.OnToFocusedChanged(focus)) }
        binding.swap.setOnClickListener { viewModel.onEvent(ConverterEvent.OnSwap) }
        binding.tryAgain.setOnClickListener { viewModel.onEvent(ConverterEvent.OnReTry)}
        binding.details.setOnClickListener{
            findNavController().navigate(R.id.details)
        }
        return binding.root
    }

}