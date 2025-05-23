package no.uio.ifi.in2000.natalan.havvarselapp.model.spot

data class PredefinedSpots(
    val coordinates: String, //The coordinate of the spot
    val spotName: String, //The name of the spot (example: "Hamresanden")
    val cityName: String, //The city the spot is in (example: "Kristiansand")
    val optimalWindConditions: Map<String, Double>, //Min and max value for wind direction
    val spotImage: Int
)
