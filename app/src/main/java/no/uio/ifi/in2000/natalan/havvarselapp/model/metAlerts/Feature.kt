package no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts

import com.fasterxml.jackson.annotation.JsonProperty

data class Feature(
    val geometry: Geometry,
    val properties: Properties,
    val type: String,
    @JsonProperty("when")
    val whenField: When,
)

