package com.josef.currency.presentation.converter

import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josef.currency.domain.usecases.convert.ConvertUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(private val convertUseCases: ConvertUseCases): ViewModel() {
    private val _converterState = MutableStateFlow(ConverterState())
    val converterState = _converterState

    init {
        fetchSymbol()
    }

    private fun fetchSymbol(){
        viewModelScope.launch {
            convertUseCases.fetchSymbols{resultMap, exception ->
                converterState.value = _converterState.value.copy(
                    currencies = resultMap.toList().map { it.first }
                )
            }
        }
    }

}



data class ConverterState(
    var currencies:List<String> = emptyList(),
    var selected_to:Int = 0,
    var selected_from:Int = 0,
    var fromAmount:String = "1",
    var ToAmount:String = "0.0"
)