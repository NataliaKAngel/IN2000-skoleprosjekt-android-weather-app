package no.uio.ifi.in2000.natalan.havvarselapp.model.spot

data class SpotInfo(
    val date: String, // a readable date e.g. "25.April"
    val time: String, // a readable time stamp e.g. "13:00"
    val windSpeedValue: Double?, // a readable windspeed e.g. "13.5"
    val windDirectionValue: Double?, // wind direction as double, e.g. "180.0"
    val windDirectionString: String?, // The wind direction as a string e.g. "vest"
    val kiteRecommendationColor: String?, // our kite recommendation represented as a color
    val kiteRecommendationSmallThumb: Int, // our kite recommendation represented as a small colored thumb
    val kiteRecommendationBigThumb: Int, // out kite recommendation represented as a big colored thumb
    val kiteRecommendationColorDrawable: androidx.compose.ui.graphics.Color, //
    val kiteWindDirectionArrowDrawable: Int? //
)
