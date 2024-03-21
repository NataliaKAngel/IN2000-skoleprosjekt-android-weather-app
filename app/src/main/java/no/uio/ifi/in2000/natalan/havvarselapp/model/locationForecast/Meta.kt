package no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast

import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val updatedAt: String? = null,
    val units: Map<String?, String?>
)