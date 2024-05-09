package no.uio.ifi.in2000.natalan.havvarselapp.model.spot

import androidx.compose.ui.graphics.Color

data class SpotInfo(
    val date: String, // a readable date e.g. "25.April"
    val time: String, // a readable time stamp e.g. "13:00"
    val windSpeedValue: Double?, // a readable windspeed e.g. "13.5"
    val windDirectionValue: Double?, // wind direction as double, e.g. "180.0"
    val windDirectionString: String?, // The wind direction as a string e.g. "vest"
    val kiteRecommendationColorString: String?, // our kite recommendation represented as a string
    val kiteRecommendationSmallThumb: Int, // our kite recommendation represented as a small colored thumb
    val kiteRecommendationBigThumb: Int, // out kite recommendation represented as a big colored thumb
    val kiteRecommendationColorDrawable: Color, //our kite recommendation represented as a color
    val kiteWindDirectionArrowDrawable: Int //Arrow icon for wind direction
)
