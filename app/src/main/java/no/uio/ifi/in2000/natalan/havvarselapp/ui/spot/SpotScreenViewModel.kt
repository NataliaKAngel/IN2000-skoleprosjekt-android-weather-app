package no.uio.ifi.in2000.natalan.havvarselapp.ui.spot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository
import no.uio.ifi.in2000.natalan.havvarselapp.model.predefinedSpots.PredefinedSpots
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.Spot

data class SpotUIState(
    val spots: Map<PredefinedSpots, Spot?> = emptyMap()
)

class SpotScreenViewModel (
    private val weatherAPIRepository: WeatherAPIRepository
) : ViewModel() {
    //Private UI-state (Map<PredefinedSpots, Spot?>)
    private val _spotUIState = MutableStateFlow(SpotUIState())

    //UI-state offered to the SpotScreen
    var spotUIState: StateFlow<SpotUIState> = _spotUIState.asStateFlow()

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