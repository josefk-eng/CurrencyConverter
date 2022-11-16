package com.josef.currency.presentation.historicalRates

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josef.currency.domain.getDaysAgo
import com.josef.currency.domain.modals.historicalData.HistoricalData
import com.josef.currency.domain.usecases.historicalRates.HistoricalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoricalViewModel @Inject constructor(private val historicalUseCase: HistoricalUseCase):ViewModel() {

    private val _historicState = MutableStateFlow(HistoricState())
    val historicState = _historicState

    fun getHistory(){
        viewModelScope.launch {
            historicalUseCase.getHistoricalRatesUseCase(
                start_date = getDaysAgo(3,"YYYY-MM-dd"),
                end_date = getDaysAgo(1,"YYYY-MM-dd")
            ){map,exception->
                toggleLoader(false)
                exception?.let {
                    _historicState.value = historicState.value.copy(
                        error = it?.message ?: "An error has occurred"
                    )
                }
                if (map.isNotEmpty()){
                    val historicList = map.map {
                        HistoricalData(it.key,(it.value as Map<String, Double>).toList())
                    }
                    _historicState.value = historicState.value.copy(
                        error = "",
                        data = historicList
                    )
                }
            }
        }
    }

    private fun toggleLoader(isLoading:Boolean){
        _historicState.value = historicState.value.copy(
            dataLoading = isLoading
        )
    }
}

data class HistoricState(
    val dataLoading:Boolean = true,
    val data:List<HistoricalData> = emptyList(),
    val error:String = ""
)


