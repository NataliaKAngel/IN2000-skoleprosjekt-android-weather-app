package no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val type: String? = null,
    val geometry: Geometry? = null,
    val properties: Properties? = null,
    val windSpeed: Double? = null,
    val windDirection: Double? = null
)
