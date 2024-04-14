package no.uio.ifi.in2000.natalan.havvarselapp.model.spot

data class Spot (
    //The coordinates of the spot
    val coordinates: String,
    //The name of the spot (example: "Hamresanden")
    val spotName: String,
    //The city the spot is in (example: "Kristiansand")
    val cityName: String,
    //The name of the area the spot is a part of (from MetAlert)
    val areaName: String,
    //Photo of the spot as URL
    val photo: String,


    //LOCATION-FORECAST:
    //Map containing windSpeed: Map<Time, WindSpeed>
    val windSpeed : Map<String, Double>,
    //Map containing windDirection: Map<Time, WindDirection>
    val windDirection : Map<String, Double>,
    //Map containing units:
    val units: Map<String?, String?>,

    //MET-ALERT:
    val riskMatrixColor: String,
    val awarenessSeriousness: String,

    //CALCULATION:
    //Recommended windDirection for the spot
    val bestWindDirection : Double,
    val recommendationColor: String,
)