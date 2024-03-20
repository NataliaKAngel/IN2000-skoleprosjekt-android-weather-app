package no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts

import no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts.Feature

data class MetAlertDataClass(
    val features: List<Feature>,
    val lang: String,
    val lastChange: String,
    val type: String,
)
