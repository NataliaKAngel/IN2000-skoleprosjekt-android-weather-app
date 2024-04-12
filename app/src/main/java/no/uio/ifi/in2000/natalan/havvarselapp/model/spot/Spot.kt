package no.uio.ifi.in2000.natalan.havvarselapp.model.spot

data class Spot (
    //The coordinates of the spot
    val coordinates: String,
    //The name of the area the spot is a part of (from MetAlert)
    val areaName: String,
    //Photo of the spot
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
    val bestWindDirection : Double
)