package no.uio.ifi.in2000.natalan.havvarselapp.ui.home

import androidx.lifecycle.ViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository

class HomeScreenViewModel(
    val weatherAPIRepository: WeatherAPIRepository
) : ViewModel(){
}