package no.uio.ifi.in2000.natalan.havvarselapp.model.spot

import androidx.compose.ui.graphics.Color


data class AlertInfo(
    val riskMatrixColor : Color?, // Alert color, the overall evaluation from the metAlert API based off the different parameters
    val description : String?, // Detailed description about the alert for the coordinates.
    val event : String?, // The event type e.g. "gale (kuling)"
    val endTime : String? // the assumed end time for the alert
)

