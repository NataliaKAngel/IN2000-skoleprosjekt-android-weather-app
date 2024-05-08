package no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForcast.LocationForecastRepository
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForcast.WeatherResponse

data class UIStateLocation(
    val lfDataMap: Map<String, WeatherResponse?> = emptyMap()
)

class LocationForestViewModel : ViewModel() {
    private val locationForecastRepository = LocationForecastRepository()

    private val _locationUIState = MutableStateFlow(UIStateLocation())
    val locationUIState: StateFlow<UIStateLocation> = _locationUIState

    var lat: String = "58.186"
    var lon: String = "8.072"

    init {
        fetchWeatherResponse(lat, lon)
    }

    fun fetchWeatherResponse(lat: String, lon: String) {
        viewModelScope.launch {
            val weatherResponse = locationForecastRepository.getLocationForecast(lat, lon, null)
            val coordinates = "$lat, $lon"

            weatherResponse?.let { response ->
                _locationUIState.value = _locationUIState.value.copy(lfDataMap = _locationUIState.value.lfDataMap + (coordinates to response))
            }
        }
    }

    fun changeCoordinates(lat: String, lon: String) {
        this.lat = lat
        this.lon = lon
        fetchWeatherResponse(lat, lon)
    }
}
