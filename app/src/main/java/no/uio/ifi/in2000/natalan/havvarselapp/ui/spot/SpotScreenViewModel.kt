package no.uio.ifi.in2000.natalan.havvarselapp.ui.spot

import androidx.lifecycle.ViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository

class SpotScreenViewModel (
    val weatherAPIRepository: WeatherAPIRepository
) : ViewModel()
{
}