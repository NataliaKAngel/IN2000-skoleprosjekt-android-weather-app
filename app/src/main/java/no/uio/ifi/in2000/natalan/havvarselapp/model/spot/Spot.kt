package no.uio.ifi.in2000.natalan.havvarselapp.model.spot

data class Spot (
    //The coordinates of the spot
    val coordinates: String = "",
    //The name of the spot
    val spotName: String = "",
    //The city ("kommune") the spot lies in
    val city: String = "",
    //The name of the area the spot is a part of (from MetAlert)
    val areaName: String = "",
    //Photo of the spot
    val photo: String = "",


    //LOCATIONFORECAST:
    //Map containing windSpeed: Map<Time, WindSpeed>
    val windSpeed : Map<String, Double> = emptyMap(),
    //Map containing windDirection: Map<Time, WindDirection>
    val windDirection : Map<String, Double> = emptyMap(),
    //Map containing units:
    val units: Map<String?, String?> = emptyMap(),

    //METALERT:
    val riskMatrixColor: String = "",
    val awarenessSeriousness: String = "",

    //CALCULATION:
    //Recommended windDirection for the spot
    val bestWindDirection : Double = 0.0
)