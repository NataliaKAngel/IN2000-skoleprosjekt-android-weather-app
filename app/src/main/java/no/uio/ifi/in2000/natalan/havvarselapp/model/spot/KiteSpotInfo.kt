package no.uio.ifi.in2000.natalan.havvarselapp.model.spot

data class KiteSpotInfo(
    val date : String,
    val time : String,
    val windSpeed : String,
    val windSpeedUnit : String,
    val windDirectionDegree: Double,
    val windDirectionUnit : String,
    val windDirectionString : String,
    val kiteRecommendationColor : String
)
