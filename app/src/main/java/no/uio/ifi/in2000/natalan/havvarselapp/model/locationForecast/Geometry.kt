package no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast

import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    val type: String,
    val coordinates: List<Double>,
)