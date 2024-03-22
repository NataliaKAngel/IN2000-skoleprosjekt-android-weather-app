package no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast

import kotlinx.serialization.Serializable

@Serializable
data class Instant(
    val details: Map<String?, Double>
)
