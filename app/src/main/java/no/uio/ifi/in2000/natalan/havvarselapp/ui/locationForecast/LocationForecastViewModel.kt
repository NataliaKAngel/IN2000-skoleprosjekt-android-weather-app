package no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// TODO: Roten til API-kall problemet -->
//  løsning: UIStateLocation holder en variabel f.eks: "values",
//  hvor man får alle verdiene samla i en liste.
//  Gjøre et API-kall som tar all dataen og transformer den og legger den i en liste
//  Dette på må skje i repository, også må UIStateLocation hente fra Repository sin metode.
data class UIStateLocation (
    val windSpeedMap: Map<String, Double> = emptyMap(),
    val airTemperatureMap: Map<String, Double> = emptyMap(),
    val airPressureMap: Map<String, Double> = emptyMap(),
    val windDirectionMap: Map<String, Double> = emptyMap(),
    val units: Map<String?, String?>? = null
)
class LocationForecastViewModel(
    //private val locationForecastRepository: LocationForecastRepository
) : ViewModel() {

    //UI State:
    private val _locationUIState = MutableStateFlow(UIStateLocation())
    val locationUIState: StateFlow<UIStateLocation> = _locationUIState.asStateFlow()

    // TODO: Remove this when methods in repository is done and viewModel offers UI-states for screen


    init {
        fetchWeatherResponses("10", "60.1")
    }

    // Method to fetch weather responses for given coordinates

    // TODO: Om skrive denne til å passe med den nye UIStateLocation
    private fun fetchWeatherResponses(latitude: String, longitude: String, altitude: String? = null) {
        viewModelScope.launch {
//            val windSpeedMap = locationForecastRepository.getWeatherResponseWindSpeedMap(latitude, longitude, altitude)
//            val airTemperatureMap = locationForecastRepository.getWeatherResponseAirTemperature(latitude, longitude, altitude)
//            val airPressureMap = locationForecastRepository.getWeatherResponseAirPressure(latitude, longitude, altitude)
//            val windDirectionMap = locationForecastRepository.getWeatherResponseWindDirection(latitude, longitude, altitude)
//            val units = locationForecastRepository.getWeatherResponseUnit(latitude, longitude, altitude)

            // Update UIStateLocation with fetched data
//            _locationUIState.value = UIStateLocation(
//                windSpeedMap,
//                airTemperatureMap,
//                airPressureMap,
//                windDirectionMap,
//                units
           // )
        }
    }
}