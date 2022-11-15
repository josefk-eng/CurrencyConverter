package com.josef.currency.presentation.converter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
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
        binding.from.onItemSelectedListener = object: OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(context,"$p2", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        return binding.root
    }
}