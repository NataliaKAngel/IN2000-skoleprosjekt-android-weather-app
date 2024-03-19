package no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts


import com.fasterxml.jackson.annotation.JsonProperty

class Properties(
    @JsonProperty("altitude_above_sea_level")
    val altitudeAboveSeaLevel: Long,
    val area: String,
    val awarenessResponse: String,
    val awarenessSeriousness: String,
    @JsonProperty("awareness_level")
    val awarenessLevel: String,
    @JsonProperty("awareness_type")
    val awarenessType: String,
    @JsonProperty("ceiling_above_sea_level")
    val ceilingAboveSeaLevel: Long,
    val certainty: String,
    val consequences: String,
    val contact: String,
    val county: List<String>,
    val description: String,
    val event: String,
    val eventAwarenessName: String,
    val eventEndingTime: String?,
    val geographicDomain: String?,
    val id: String,
    val instruction: String,
    val resources: List<Resource>,
    val riskMatrixColor: String,
    val severity: String,
    val status: String,
    val title: String,
    val triggerLevel: String?,
    val type: String,
    val web: String,
    val municipality: List<String>?,
)