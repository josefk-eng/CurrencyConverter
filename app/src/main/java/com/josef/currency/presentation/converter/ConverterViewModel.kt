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
        setInProgress()
        viewModelScope.launch {
            convertUseCases.fetchSymbols{resultMap, exception ->
                exception?.let {
                    converterState.value = _converterState.value.copy(
                        error = it.message ?: "Something wrong has occurred",
                        inProgress = false
                    )
                }
                converterState.value = _converterState.value.copy(
                    currencies = resultMap.toList().map { it.first },
                    error = "",
                    inProgress = false
                )
            }
        }
    }

    fun convert(fromPos:Int, toPos:Int){
        setInProgress()
        viewModelScope.launch {
            convertUseCases.convert(
                converterState.value.fromAmount,
                converterState.value.currencies[fromPos],
                converterState.value.currencies[toPos],
                ""){response, exception->
                exception?.let {
                    converterState.value = _converterState.value.copy(
                        error = it.message ?: "Something wrong has occurred",
                        inProgress = false
                    )
                }
                response?.let {
                    val conversion = it.info.rate * converterState.value.fromAmount.toDouble()
                    converterState.value = _converterState.value.copy(
                        toAmount = "$conversion",
                        error = "",
                        inProgress = false
                    )
                }
            }
        }
    }
    private fun setInProgress(){
        converterState.value = _converterState.value.copy(
            inProgress = true
        )
    }
}



data class ConverterState(
    var currencies:List<String> = emptyList(),
    var selected_to:Int = 0,
    var selected_from:Int = 0,
    var fromAmount:String = "1",
    var toAmount:String = "1.0",
    var error:String = "",
    var inProgress:Boolean = false
)