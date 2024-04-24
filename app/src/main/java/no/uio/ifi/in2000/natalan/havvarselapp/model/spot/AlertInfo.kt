package no.uio.ifi.in2000.natalan.havvarselapp.model.spot


data class AlertInfo(
    val riskMatrixColor : String,
    val description : String,
    val event : String,
    val startTime : String,
    val endTime : String
)

