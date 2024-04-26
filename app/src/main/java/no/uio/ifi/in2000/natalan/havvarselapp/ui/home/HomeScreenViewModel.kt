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

    private fun getCorrectTimeStamp(spot: Spot) : Int{
        val timestamp = System.currentTimeMillis().toString()
        val spotInfo = spot.spotDetails[0]//spot.spotDetails.find{it.timestamp == timestamp}
        println(spotInfo.kiteRecommendationColor).toString()
        return when(spotInfo.kiteRecommendationColor){
            "grey" -> R.drawable.sgreythumb
            "blue" -> R.drawable.sbluethumb
            "green" -> R.drawable.sgreenthumb
            "yellow" -> R.drawable.syellowthumb
            "orange" -> R.drawable.sorangethumb
            "red" -> R.drawable.sredthumb
            else -> R.drawable.sgreythumb
        }
    }

    private fun getThumbs(spots: List<Spot>) : Map<String, Int>{
        return spots.associate { spot ->
            spot.predefinedSpot.coordinates to getCorrectTimeStamp(spot)
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