package com.josef.currency.presentation.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.josef.currency.R
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
        return binding.root
    }
}