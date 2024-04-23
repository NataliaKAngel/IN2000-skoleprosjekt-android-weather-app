package no.uio.ifi.in2000.natalan.havvarselapp.model.spot

import no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast.WeatherResponse
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.Feature
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertDataClass

data class Spot (
    //The coordinates of the spot
    val coordinates: String,
    //The name of the spot (example: "Hamresanden")
    val spotName: String,
    //The city the spot is in (example: "Kristiansand")
    val cityName: String,
    //Recommended windDirection for the spot
    val optimalWindConditions: Map<String, Double>, //Min and max value for wind direction
    //Photo of the spot as URL
    val photo: String,


    //LOCATION-FORECAST:
    //Map containing windSpeed: Map<Time, WindSpeed>
    val windSpeed : Map<String, Double>,
    val windSpeedUnit: String?,
    //Map containing windDirection: Map<Time, WindDirection>
    val windDirection : Map<String, Double>,
    val windDirectionUnit: String?,

    //MET-ALERT:
    //Color of the alert
    val riskMatrixColor: String?,
    //Description for the alert
    val description: String?,
    //Wind speed level for triggering the alert
    val triggerLevel: String?,

    //CALCULATION:
    //Color recommendation for kiting
    val recommendationColor: String,
)