package no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast

import kotlinx.serialization.Serializable

@Serializable
data class Next12HoursData(
    val summary: Map<String?, String?>?,
    val details: Map<String?, Double?>?
)
