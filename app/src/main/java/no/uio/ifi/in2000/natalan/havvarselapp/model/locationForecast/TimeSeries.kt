package no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast

import kotlinx.serialization.Serializable

@Serializable
data class TimeSeries(
    val time: String,
    val data: Data
)