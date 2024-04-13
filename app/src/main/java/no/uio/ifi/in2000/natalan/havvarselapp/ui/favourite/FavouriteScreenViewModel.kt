package no.uio.ifi.in2000.natalan.havvarselapp.ui.favourite

import androidx.lifecycle.ViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository

class FavouriteScreenViewModel (
    val weatherAPIRepository: WeatherAPIRepository
) : ViewModel()
{

}