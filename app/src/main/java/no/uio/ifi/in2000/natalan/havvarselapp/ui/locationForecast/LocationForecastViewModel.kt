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
    val lfDataMap: Map<String, WeatherResponse?> =  emptyMap()
)
class LocationForecastViewModel(
    private val locationForecastRepository: LocationForecastRepository
) : ViewModel() {
    //val locationForecastRepository = LocationForecastRepository()

    private val _locationUIState = MutableStateFlow(UIStateLocation())
    val locationUIState: StateFlow<UIStateLocation> = _locationUIState.asStateFlow()

    init {
        fetchWeatherResponses(listOf(Pair("60.1", "9.58")))
    }
    private fun fetchWeatherResponses(locations: List<Pair<String, String>>) {
        viewModelScope.launch {
            locations.forEach { location ->
                val weatherResponse = locationForecastRepository.getLocationForecast(
                    location.first,
                    location.second,
                    null
                )

                val coordinates = "${location.first}, ${location.second}"

                // Oppdater UIStateLocation med vindinformasjonen hvis weatherResponse ikke er null
                weatherResponse?.let { response ->
                    val windSpeed = response.properties?.timeseries?.firstOrNull()?.data?.instant?.details?.get("wind_speed")
                    val windDirection = response.properties?.timeseries?.firstOrNull()?.data?.instant?.details?.get("wind_from_direction")

                    _locationUIState.update { currentState ->
                        val updatedMap = currentState.lfDataMap.toMutableMap()
                        updatedMap[coordinates] = response.copy(windSpeed = windSpeed, windDirection = windDirection)
                        UIStateLocation(updatedMap)
                    }
                }
            }
        }
    }

    @Composable
    fun LocationForestScreen(
        navController: NavController = rememberNavController()
    ) {
        Column {
            Text("Her kommer Weather and Wind API informasjon")
            Button(
                onClick = {
                    navController.navigate("metAlerts")
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Knapp til forrige skjerm")
            }
        }
    }
}
