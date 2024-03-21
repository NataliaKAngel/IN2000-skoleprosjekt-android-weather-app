package no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts

data class MetAlertDataClass( // Er navnet på denne klassen valgt selv, eller valgt ut i fra APIet? ER den valgt selv foreslår vi å endre navn, om ikke så har vi jo ikke noe valg.
    val features: List<Feature>,
    val lang: String,
    val lastChange: String,
    val type: String,
)
