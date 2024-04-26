package no.uio.ifi.in2000.natalan.havvarselapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.natalan.havvarselapp.R
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.Spot
import no.uio.ifi.in2000.natalan.havvarselapp.ui.state.SpotUIState
import no.uio.ifi.in2000.natalan.havvarselapp.ui.state.SpotsUIState
import no.uio.ifi.in2000.natalan.havvarselapp.ui.state.ThumbUIState
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.time.Duration.Companion.hours

class HomeScreenViewModel(
    private val weatherAPIRepository: WeatherAPIRepository
) : ViewModel(){
    //UI-state: List<Spot>
    private val _spotsUIState = MutableStateFlow(SpotsUIState())
    var spotsUIState: StateFlow<SpotsUIState> = _spotsUIState.asStateFlow()

    //UI-state: Spot?
    private val _spotUIState = MutableStateFlow(SpotUIState())
    var spotUIState: StateFlow<SpotUIState> = _spotUIState.asStateFlow()


    // UI-state: Thumbs
    private val _thumbsUIState = MutableStateFlow(ThumbUIState())
    var thumbUIState : StateFlow<ThumbUIState> = _thumbsUIState.asStateFlow()

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


    private fun getCorrectThumbIcon(spot: Spot) : Int{
        val currentTime = LocalTime.now()
        val hour = currentTime.hour.toString()

        spot.spotDetails.forEach { spotInfo ->
            val (spotHour, spotMinute) = spotInfo.time.split(".")
            if (spotHour == hour){
                return spotInfo.kiteRecommendationSmallThumb
            }
        }
        return R.drawable.sgreythumb
    }

    private fun getThumbs(spots: List<Spot>) : Map<String, Int>{
        return spots.associate { spot ->
            spot.predefinedSpot.coordinates to getCorrectThumbIcon(spot)
        }
    }

    fun updateThumbsUIState(spots: List<Spot>){
        _thumbsUIState.update {
            it.copy(
                thumbs = getThumbs(spots)
            )
        }
    }
}