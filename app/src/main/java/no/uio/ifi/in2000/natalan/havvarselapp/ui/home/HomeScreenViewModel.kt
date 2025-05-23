package no.uio.ifi.in2000.natalan.havvarselapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository
import no.uio.ifi.in2000.natalan.havvarselapp.ui.state.ClickedUIState
import no.uio.ifi.in2000.natalan.havvarselapp.ui.state.SpotUIState
import no.uio.ifi.in2000.natalan.havvarselapp.ui.state.SpotsUIState

class HomeScreenViewModel(
    private val weatherAPIRepository: WeatherAPIRepository
) : ViewModel(){
    //UI-state: List<Spot>
    private val _spotsUIState = MutableStateFlow(SpotsUIState())
    var spotsUIState: StateFlow<SpotsUIState> = _spotsUIState.asStateFlow()

    //UI-state: Spot?
    private val _spotUIState = MutableStateFlow(SpotUIState())
    var spotUIState: StateFlow<SpotUIState> = _spotUIState.asStateFlow()

    // UI-state: Clicked
    private val _clickedUIState = MutableStateFlow(ClickedUIState())
    var clickedUIState : StateFlow<ClickedUIState> = _clickedUIState.asStateFlow()

    //Getting data asynchronous from weatherAPIRepository and updates private UI-state
    init {
        viewModelScope.launch {
            _spotsUIState.update {
                it.copy(
                    spots = weatherAPIRepository.getAllSpots()
                )
            }
        }
    }

    //HomeScreen can use this method to update spotUIState to hold the correct Spot-object
    fun updateSpotUIState(coordinates: String){
        viewModelScope.launch {
            _spotUIState.update {
                it.copy(
                    spot = weatherAPIRepository.getOneSpot(coordinates)
                )
            }
        }
    }

    fun updateClickedUIState(clicked: Boolean){
        _clickedUIState.update {
            it.copy(
                clicked = clicked
            )
        }
    }
}