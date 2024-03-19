package no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts

import com.fasterxml.jackson.annotation.JsonProperty
import no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts.Properties
import no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts.When

data class Feature(
    val geometry: Geometry,
    val properties: Properties,
    val type: String,
    @JsonProperty("when")
    val when_field: When,
)

