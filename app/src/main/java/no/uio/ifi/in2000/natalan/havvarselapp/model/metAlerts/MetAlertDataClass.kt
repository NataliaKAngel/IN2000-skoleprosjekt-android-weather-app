package no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts

data class MetAlertDataClass(
    val features: List<Feature>,
    val lang: String,
    val lastChange: String,
    val type: String,
)
