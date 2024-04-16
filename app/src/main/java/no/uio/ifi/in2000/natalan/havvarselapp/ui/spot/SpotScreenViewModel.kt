package no.uio.ifi.in2000.natalan.havvarselapp.ui.spot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository
import no.uio.ifi.in2000.natalan.havvarselapp.ui.state.SpotsUIState

class SpotScreenViewModel (
    private val weatherAPIRepository: WeatherAPIRepository
) : ViewModel() {
    //Private UI-state (Map<PredefinedSpots, Spot?>)
    private val _spotUIState = MutableStateFlow(SpotsUIState())

    //UI-state offered to the SpotScreen
    var spotUIState: StateFlow<SpotsUIState> = _spotUIState.asStateFlow()

    //Getting data asynchronous from weatherAPIRepository and updates private UI-state
    init {
        viewModelScope.launch {
            _spotUIState.update {
            it.copy(
                spots = weatherAPIRepository.getPredefinedSpots()
                )
            }
        }
    }
}