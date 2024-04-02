package no.uio.ifi.in2000.natalan.havvarselapp.Data.Havvarsel

import com.fasterxml.jackson.annotation.JsonProperty

data class Feature(
    val geometry: Geometry,
    val properties: Properties,
    val type: String,
    @JsonProperty("when")
    val when_field: When,
)

