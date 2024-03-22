package no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast

import kotlinx.serialization.Serializable

@Serializable
data class Properties(
    val meta: Meta,
    val timeseries: List<TimeSeries>
)