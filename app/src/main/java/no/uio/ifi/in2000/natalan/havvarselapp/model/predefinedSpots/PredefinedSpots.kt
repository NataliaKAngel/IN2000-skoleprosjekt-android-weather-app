package no.uio.ifi.in2000.natalan.havvarselapp.model.predefinedSpots

data class PredefinedSpots(
    val coordinate: String, //The coordinate of the spot
    val spotName: String, //The name of the spot (example: "Hamresanden")
    val cityName: String //The city the spot is in (example: "Kristiansand")
)
