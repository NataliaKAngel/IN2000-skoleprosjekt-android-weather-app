package no.uio.ifi.in2000.natalan.havvarselapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository
import no.uio.ifi.in2000.natalan.havvarselapp.ui.state.SpotUIState
import no.uio.ifi.in2000.natalan.havvarselapp.ui.state.SpotsUIState

class HomeScreenViewModel(
    private val weatherAPIRepository: WeatherAPIRepository
) : ViewModel(){
    //UI-state: Map<PredefinedSpots, Spot?>
    private val _spotsUIState = MutableStateFlow(SpotsUIState())
    var spotsUIState: StateFlow<SpotsUIState> = _spotsUIState.asStateFlow()

    //UI-state: Spot?
    private val _spotUIState = MutableStateFlow(SpotUIState())
    var spotUIState: StateFlow<SpotUIState> = _spotUIState.asStateFlow()

    //Getting data asynchronous from weatherAPIRepository and updates private UI-state
    init {
        viewModelScope.launch {
            _spotsUIState.update {
                it.copy(
                    spots = weatherAPIRepository.getPredefinedSpots()
                )
            }
        }
    }
}