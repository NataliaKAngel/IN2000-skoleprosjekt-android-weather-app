package no.uio.ifi.in2000.natalan.havvarselapp.ui.home

import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForcast.WeatherResponse

data class LFUIState (
    val locations: List<WeatherResponse> = listOf()
)