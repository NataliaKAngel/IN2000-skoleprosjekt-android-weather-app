package no.uio.ifi.in2000.natalan.havvarselapp.ui.info

import androidx.lifecycle.ViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository

class InfoScreenViewModel(
    val weatherAPIRepository: WeatherAPIRepository
) : ViewModel() {
}