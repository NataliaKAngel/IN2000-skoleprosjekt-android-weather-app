package no.uio.ifi.in2000.natalan.havvarselapp.ui.state

import no.uio.ifi.in2000.natalan.havvarselapp.model.predefinedSpots.PredefinedSpots
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.Spot

data class SpotsUIState(
    val spots: Map<PredefinedSpots, Spot?> = emptyMap()
)
