package no.uio.ifi.in2000.natalan.havvarselapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForcast.LocationForecastDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForcast.LocationForecastRepository

class HomeScreenViewModel: ViewModel() {
    private val locationForecastdataSource = LocationForecastDataSource()
    private val locationForecastRepository = LocationForecastRepository()

    private val _lfUIState = MutableStateFlow(LFUIState())
    val lfUIState: StateFlow<LFUIState> = _lfUIState.asStateFlow()

    private var APICalled = false // For aa hente kun en API
    init {
        if (!APICalled) { // Hente en API
            viewModelScope.launch {
                Log.d("call", "API kall skjer")
                // val lf = locationForecastRepository.getLocationForecast()
                APICalled = true
            }
        }
    }
}