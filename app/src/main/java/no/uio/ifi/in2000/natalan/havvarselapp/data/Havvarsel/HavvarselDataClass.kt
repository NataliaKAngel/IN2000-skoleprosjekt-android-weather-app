package no.uio.ifi.in2000.natalan.havvarselapp.Data.Havvarsel

data class HavvarselDataClass (
    val features: List<Feature>,
    val lang: String,
    val lastChange: String,
    val type: String,
)