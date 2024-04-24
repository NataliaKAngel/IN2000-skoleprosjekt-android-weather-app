package no.uio.ifi.in2000.natalan.havvarselapp.model.spot

data class SpotInfo(
    val date : String, // a readable date e.g. "25.April"
    val time : String, // a readable time stamp e.g. "13:00"
    val windSpeed : String, // a readable windspeed e.g. "13.5"
    val windSpeedUnit : String, // Unit: m/s
    val windDirectionDegree: Double, // wind direction as double, e.g. "180.0"
    val windDirectionUnit : String, //Unit: degrees
    val windDirectionString : String, // The wind direction as a string e.g. "vest"
    val kiteRecommendationColor : String // our kite recommendation represented as a color e.g. "yellow"
)
