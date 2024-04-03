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
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForecast.LocationForecastRepository
import no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast.WeatherResponse

data class UIStateLocation (
    val windSpeedMap: Map<String, Double> = emptyMap(),
    val airTemperatureMap: Map<String, Double> = emptyMap(),
    val airPressureMap: Map<String, Double> = emptyMap(),
    val windDirectionMap: Map<String, Double> = emptyMap(),
    val units: Map<String?, String?>? = null
)
class LocationForecastViewModel(
    private val locationForecastRepository: LocationForecastRepository
) : ViewModel() {

    //UI State: Map<String, WeatherResponse?>
    private val _locationUIState = MutableStateFlow(UIStateLocation())
    val locationUIState: StateFlow<UIStateLocation> = _locationUIState.asStateFlow()

    // TODO: Remove this when methods in repository is done and viewModel offers UI-states for screen


    init {
        fetchWeatherResponses("10", "60.1")
    }

    // Method to fetch weather responses for given coordinates

    fun fetchWeatherResponses(latitude: String, longitude: String, altitude: String? = null) {
        viewModelScope.launch {
            val windSpeedMap = locationForecastRepository.getWeatherResponseWindSpeedMap(latitude, longitude, altitude)
            val airTemperatureMap = locationForecastRepository.getWeatherResponseAirTemperature(latitude, longitude, altitude)
            val airPressureMap = locationForecastRepository.getWeatherResponseAirPressure(latitude, longitude, altitude)
            val windDirectionMap = locationForecastRepository.getWeatherResponseWindDirection(latitude, longitude, altitude)
            val units = locationForecastRepository.getWeatherResponseUnit(latitude, longitude, altitude)

            // Update UIStateLocation with fetched data
            _locationUIState.value = UIStateLocation(
                windSpeedMap,
                airTemperatureMap,
                airPressureMap,
                windDirectionMap,
                units
            )
        }
    }
}