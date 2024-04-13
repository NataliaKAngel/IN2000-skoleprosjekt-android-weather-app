package no.uio.ifi.in2000.natalan.havvarselapp.ui.settings

import androidx.lifecycle.ViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository

class SettingsScreenViewModel (
    val weatherAPIRepository: WeatherAPIRepository
) : ViewModel()
{
}