package com.josef.currency.presentation.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josef.currency.domain.usecases.convert.ConvertUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(private val convertUseCases: ConvertUseCases): ViewModel() {
    private val _converterState = MutableStateFlow(ConverterState())
    val converterState = _converterState

    var convertJob:Job? = null

    init {
        fetchSymbol()
    }

    private fun fetchSymbol(){
        loadCurrencies()
        viewModelScope.launch {
            convertUseCases.fetchSymbols{resultMap, exception ->
                exception?.let {
                    _converterState.value = converterState.value.copy(
                        error = if(it.message == null || it.message?.isEmpty() == false) it.localizedMessage else it.message ?: "Something went wrong",
                        loadingCurrencies = false
                    )
                }
                if (exception==null)
                    converterState.value = _converterState.value.copy(
                        currencies = resultMap.toList().map { it.first },
                        error = "",
                        loadingCurrencies = false
                    )
            }
        }
    }

    fun convert(amount:String, fromCurrency:String, toCurrency:String, date:String){
        convertJob?.cancel()
        convertJob = viewModelScope.launch {
            setInProgress()
            convertUseCases.convert(
                amount,
                fromCurrency,
                toCurrency,
            ) { response, exception ->
                exception?.let {
                    converterState.value = _converterState.value.copy(
                        error = it.message ?: "Something wrong has occurred",
                        inProgress = false
                    )
                }
                response?.let {
                    val conversion = String.format("%.2f", it.info.rate * amount.toDouble())
                    _converterState.value = converterState.value.copy(
                        inProgress = false,
                        error = "",
                        fromAmount = if (_converterState.value.from_to) _converterState.value.fromAmount else conversion ,
                        toAmount = if (_converterState.value.from_to) conversion else _converterState.value.toAmount
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
    private fun loadCurrencies(){
        converterState.value = _converterState.value.copy(
            loadingCurrencies = true
        )
    }
    private fun toggle_from_to(isConvertingFrom:Boolean){
        converterState.value = _converterState.value.copy(
            from_to = isConvertingFrom
        )
    }
    fun onEvent(event: ConverterEvent){
        when(event){
            is ConverterEvent.OnFromAmountChanged -> {
                if (_converterState.value.currencies.isNotEmpty() && _converterState.value.fromAmountFocused) {
                    toggle_from_to(true)
                    convert(
                        event.fromAmount,
                        _converterState.value.currencies[_converterState.value.selected_from],
                        _converterState.value.currencies[_converterState.value.selected_to],
                        ""
                    )
                }
            }
            is ConverterEvent.OnToAmountChanged -> {
                if (_converterState.value.currencies.isNotEmpty() && _converterState.value.toAmountFocused) {
                    toggle_from_to(false)
                    convert(
                        event.toAmount,
                        _converterState.value.currencies[_converterState.value.selected_to],
                        _converterState.value.currencies[_converterState.value.selected_from],
                        ""
                    )
                }
            }
            is ConverterEvent.OnSwap -> {
                val temp = _converterState.value.selected_from
                _converterState.value = converterState.value.copy(
                    selected_from = _converterState.value.selected_to,
                    selected_to = temp
                )
                convert(
                    if(_converterState.value.from_to) _converterState.value.fromAmount else _converterState.value.toAmount,
                    _converterState.value.currencies[_converterState.value.selected_from],
                    _converterState.value.currencies[_converterState.value.selected_to],
                    ""
                )
            }
            is ConverterEvent.OnReTry -> {
                if (_converterState.value.currencies.isEmpty()){
                    fetchSymbol()
                }else{
                    convert(
                        if (_converterState.value.from_to) _converterState.value.fromAmount else _converterState.value.toAmount,
                        _converterState.value.currencies[_converterState.value.selected_from],
                        _converterState.value.currencies[_converterState.value.selected_to],
                        ""
                    )
                }
            }
            is ConverterEvent.OnFromFocusedChanged -> {
                _converterState.value = converterState.value.copy(
                    fromAmountFocused = event.hasFocus,
                )
            }
            is ConverterEvent.OnToFocusedChanged -> {
                _converterState.value = converterState.value.copy(
                    toAmountFocused = event.hasFocus
                )
            }
        }
    }
}

sealed class ConverterEvent{
    data class OnFromAmountChanged(val fromAmount:String):ConverterEvent()
    data class OnToAmountChanged(val toAmount:String):ConverterEvent()
    data class OnFromFocusedChanged(val hasFocus:Boolean):ConverterEvent()
    data class  OnToFocusedChanged(val hasFocus:Boolean):ConverterEvent()
    object OnSwap:ConverterEvent()
    object OnReTry:ConverterEvent()
}

data class ConverterState(
    var currencies:List<String> = emptyList(),
    var selected_to:Int = 0,
    var selected_from:Int = 0,
    var fromAmount:String = "1",
    var toAmount:String = "1",
    var error:String = "",
    var loadingCurrencies:Boolean = false,
    var inProgress:Boolean = false,
    var from_to:Boolean = true,
    var fromAmountFocused:Boolean = false,
    var toAmountFocused:Boolean = false,
)