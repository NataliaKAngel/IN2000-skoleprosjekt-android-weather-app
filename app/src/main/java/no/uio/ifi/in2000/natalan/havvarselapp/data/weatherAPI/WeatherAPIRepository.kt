package no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI

import androidx.compose.ui.graphics.Color
import no.uio.ifi.in2000.natalan.havvarselapp.R
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.locationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.metAlerts.MetAlertsDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.predefinedSpots.PredefinedSpotsDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast.WeatherResponse
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.Feature
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertDataClass
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.PredefinedSpots
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.AlertInfo
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.SpotInfo
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.Spot
import java.text.SimpleDateFormat
import java.util.Locale
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.*

class WeatherAPIRepository (
    private val predefinedSpotsDataSource: PredefinedSpotsDataSource,
    private val locationForecastDataSource: LocationForecastDataSource,
    private val metAlertsDataSource: MetAlertsDataSource
){
    //METHODS TO CREATE OBJECTS
    //Creates: List<Spot>
    private suspend fun createAllSpots(): List<Spot>{
        //Creates one Spot-object for each PredefinedSpot-object
        return getPredefinedSpots().map { createOneSpot(it) }
    }

    private suspend fun createOneSpot(predefinedSpot: PredefinedSpots): Spot {
        //Gets data from LocationForecast
        val weatherResponse = getWeatherResponse(predefinedSpot.coordinates)
        val windSpeed = getWindSpeedMap(weatherResponse)
        val windDirection = getWindDirectionMap(weatherResponse)

        //Gets data from MetAlerts
        val features = getMetAlerts(predefinedSpot.coordinates)?.features
        val alerts = createAllAlertInfos(features)

        //Combines data from LocationForecast and MetAlerts
        val spotdetails = createAllSpotInfos(alerts, windSpeed, windDirection, predefinedSpot.optimalWindConditions)

        //Creates and returns one Spot-object
        return Spot(
            predefinedSpot = predefinedSpot,
            alerts = alerts,
            spotDetails = spotdetails
        )
    }

    private fun createAllAlertInfos(features: List<Feature>?): List<AlertInfo?> {
        //Creates one AlertInfo-object for each Feature-object
        return features?.map { createOneAlertInfo(it) } ?: emptyList()
    }

    private fun createOneAlertInfo(feature: Feature?): AlertInfo {
        //Creates and returns one AlertInfo-object
        return AlertInfo(
            riskMatrixColor = feature?.properties?.riskMatrixColor?.let { getAlertColor(it) },
            description = feature?.properties?.description,
            event = feature?.properties?.event,
            endTime = feature?.properties?.eventEndingTime
        )
    }

    private fun getAlertColor(riskMatrixColor: String): Color {
        //Returns the correct Color-object
        return when(riskMatrixColor){
            "Yellow" -> YellowCircle
            "Orange" -> OrangeCircle
            "Red" -> RedCircle
            else -> White
        }
    }

    private fun createAllSpotInfos(alerts: List<AlertInfo?>, windSpeed: Map<String, Double>, windDirection: Map<String, Double>, optimalWindConditions: Map<String, Double>): List<SpotInfo> {
        //Creates one SpotInfo-object for each time stamp in the LocationForecast-API
        return windSpeed.keys.map { timeStamp ->
            createOneSpotInfo(alerts, windSpeed, windDirection, optimalWindConditions, timeStamp)
        }
    }

    private fun createOneSpotInfo(alerts: List<AlertInfo?>, windSpeed: Map<String, Double>, windDirection: Map<String, Double>, optimalWindConditions: Map<String, Double>, timeStamp: String): SpotInfo{
        //Create readable date and time
        val (date, time) = timeStamp.split("T") //Timestamp format: YYYY-MM-DDThh:mm:ssZ
        val readableDate = transformDate(date)
        val readableTime = transformTime(time)

        //Gets the correct wind speed and wind direction
        val windSpeedValue = windSpeed[timeStamp]
        val windDirectionValue = windDirection[timeStamp]

        //Creates a readable wind direction string
        val windDirectionString = windDirectionValue?.let { transformWindDirection(it) }

        //Gets the correct wind direction icon
        val windDirectionIcon = getWindDirectionIcon(windDirectionString)

        //Calculate kiteRecommendationColor
        val color = calculateKiteRecommendation(alerts, windSpeedValue, windDirectionValue, optimalWindConditions, timeStamp)
        val kiteRecommendationColor = getKiteRecommendationColor(color)

        //Gets the correct thumb icons
        val kiteRecommendationSmallThumb = getSmallThumb(color)
        val kiteRecommendationBigThumb = getBigThumb(color)

        //Creates and returns one SpotInfo-object
        return SpotInfo(
            date = readableDate,
            time = readableTime,
            windSpeedValue = windSpeedValue,
            windDirectionValue = windDirectionValue,
            windDirectionString = windDirectionString,
            windDirectionIcon = windDirectionIcon,
            kiteRecommendationColorString = color,
            kiteRecommendationSmallThumb = kiteRecommendationSmallThumb,
            kiteRecommendationBigThumb = kiteRecommendationBigThumb,
            kiteRecommendationColor = kiteRecommendationColor
        )
    }

    private fun transformDate(date: String): String {
        //Date from LocationForecast: YYYY-MM-DD
        val (year, month, day) = date.split("-")

        //Creates and returns the date as a readable string in Norwegian
        return day + (when (month){
            "01" -> ". Januar"
            "02" -> ". Februar"
            "03" -> ". Mars"
            "04" -> ". April"
            "05" -> ". Mai"
            "06" -> ". Juni"
            "07" -> ". Juli"
            "08" -> ". August"
            "09" -> ". September"
            "10" -> ". Oktober"
            "11" -> ". November"
            "12" -> ". Desember"
            else -> ""
        })
    }

    private fun transformTime(time: String): String {
        //Time from LocationForecast: hh:mm:ssZ
        val (hour) = time.split(":")
        val summerTime = hour.toInt()

        //Creates and returns a timestamp as a string in Norwegian summer time
        return "${summerTime+2}.00"
    }

    private fun transformWindDirection(windDirectionValue: Double): String {
        //Returns the correct wind direction as a readable string in Norwegian
        return when (windDirectionValue) {
            in 337.5..360.0, in 0.0..22.5 -> "nord"
            in 22.5..67.5 -> "nordøst"
            in 67.5..112.5 -> "øst"
            in 112.5..157.5 -> "sørøst"
            in 157.5..202.5 -> "sør"
            in 202.5..247.5 -> "sørvest"
            in 247.5..292.5 -> "vest"
            in 292.5..337.5 -> "nordvest"
            else -> "Ugyldige grader"
        }
    }

    private fun getSmallThumb(color: String?): Int {
        //Returns the correct small thumb icon
        return when(color) {
            "grey" -> R.drawable.sgreythumb
            "blue" -> R.drawable.sbluethumb
            "green" -> R.drawable.sgreenthumb
            "yellow" -> R.drawable.syellowthumb
            "orange" -> R.drawable.sorangethumb
            "red" -> R.drawable.sredthumb
            else -> R.drawable.sgreythumb
        }
    }

    private fun getBigThumb(color: String?): Int {
        //Returns the correct big thumb icon
        return when(color) {
            "grey" -> R.drawable.bgreythumb
            "blue" -> R.drawable.bbluethumb
            "green" -> R.drawable.bgreenthumb
            "yellow" -> R.drawable.byellowthumb
            "orange" -> R.drawable.borangethumb
            "red" -> R.drawable.bredthumb
            else -> R.drawable.bgreythumb
        }
    }

    private fun calculateKiteRecommendation(alerts: List<AlertInfo?>, windSpeedValue: Double?, windDirectionValue: Double?, optimalWindConditions: Map<String, Double>, timeStamp: String): String? {
        //If the event-type in any of the AlertInfo-objects is rainFlood or lightning and the alert is current: No kite conditions (red)
        val isRedAlert = alerts.any { it?.event == "rainFlood" || it?.event == "lightning" }
        val alertIsCurrent = alerts.any { checkAlertValidity(it, timeStamp) }

        if (isRedAlert && alertIsCurrent) {
            return "red"
        }

        //Calculating if the wind direction conditions and the wind speed conditions is good for kiting
        val windDirectionCalculation = calculateWindDirection(windDirectionValue, optimalWindConditions) //String: "high", "low", "correct" or "unknown"
        val windSpeedCalculation = windSpeedValue?.let { calculateWindSpeed(it) } //String: "grey", "blue", "green", "yellow", "orange", "red" or "unknown"

        //If the wind direction is wrong or the wind speed is to slow: No kite conditions (grey)
        val wrongWindDirection = (windDirectionCalculation == "low" || windDirectionCalculation == "high")
        if (wrongWindDirection || windSpeedCalculation == "grey"){
            return "grey"
        }

        //If the wind conditions is correct: Kiting conditions (blue, green, yellow or red)
        if (windDirectionCalculation == "correct"){
            return windSpeedCalculation
        }

        return "unknown"
    }

    //Checks the alert validity based on timestamp and start and end time for the alert
    private fun checkAlertValidity(alert: AlertInfo?, timeStamp: String): Boolean {
        //If there is no end time the alert is not valid
        if (alert?.endTime == null){
            return false
        }

        //Creates a time stamp format
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())

        //Parsing endTime and timestamp to match the format created
        val endTime = alert.endTime.let { format.parse(it) }
        val timeToCheck = format.parse(timeStamp)

        //Returns true if timeToCheck is not null and if it is before endTime
        if (timeToCheck != null) {
            return timeToCheck.before(endTime)
        }
        return false
    }

    private fun calculateWindDirection(windDirectionValue: Double?, optimalWindConditions: Map<String, Double>): String{
        //Extracting maxWind and minWind from optimal wind conditions
        val minWind = optimalWindConditions["min"] ?: 0.0
        val maxWind = optimalWindConditions["max"] ?: 360.0

        //Returns "correct" if the wind direction is in the range of the optimal wind conditions
        if (windDirectionValue != null) {
            return when {
                windDirectionValue < minWind -> "low"
                windDirectionValue > maxWind -> "high"
                else -> "correct"
            }
        }
        return "unknown"
    }

    private fun calculateWindSpeed(windSpeedValue: Double): String {
        //Returns a color for the wind speed based on the optimal wind conditions for kiting
        return when {
            windSpeedValue >= 0.0 && windSpeedValue < 5.0 -> "grey"
            windSpeedValue >= 5.0 && windSpeedValue < 7.0 -> "blue"
            windSpeedValue >= 7.0 && windSpeedValue < 11.0 -> "green"
            windSpeedValue >= 11.0 && windSpeedValue < 15.0 -> "yellow"
            windSpeedValue >= 15.0 && windSpeedValue < 19.0 -> "orange"
            windSpeedValue >= 19.0 -> "red"
            else -> "unknown"
        }
    }

    private fun getKiteRecommendationColor(color: String?): Color{
        //Returns the correct Color-object
        return when (color?.lowercase()) {
            "grey" -> LightGrayCircle
            "blue" -> BlueCircle
            "green" -> GreenCircle
            "yellow" -> YellowCircle
            "orange" -> OrangeCircle
            "red" -> RedCircle
            else -> LightGrayCircle
        }
    }

    private fun getWindDirectionIcon(windDirectionString: String?): Int{
        //Returns the correct wind direction icon
        return when (windDirectionString?.lowercase()){
            "nord" -> R.drawable.arrow_north
            "nordøst" -> R.drawable.arrow_northeast
            "øst" -> R.drawable.arrow_east
            "sørøst" -> R.drawable.arrow_southeast
            "sør" -> R.drawable.arrow_south
            "sørvest" -> R.drawable.arrow_southwest
            "vest" -> R.drawable.arrow_west
            "nordvest" -> R.drawable.arrow_northwest
            else -> R.drawable.arrow_north
        }
    }

    //OFFERS UI-STATE DATA TO: ViewModel
    //Creates a list of Spot-objects and returns it.
    suspend fun getAllSpots(): List<Spot>{
        return createAllSpots()
    }

    //Returns one Spot-object based on coordinates to ViewModel
    suspend fun getOneSpot(coordinates: String): Spot {
        //Getting predefinedSpot, WeatherResponse and Feature to create Spot-object
        val predefinedSpot = getPredefinedSpots().find { it.coordinates == coordinates }

        return createOneSpot(predefinedSpot!!)
    }

    //GETS AND TRANSFORM DATA FROM: LocationForecast
    //Gets one WeatherResponse object from locationForecastDataSource
    private suspend fun getWeatherResponse(coordinates: String): WeatherResponse? {
        return locationForecastDataSource.getWeatherResponse(coordinates)
    }

    // Different methods to transform the data from a WeatherResponse.
    private fun getWindSpeedMap(weatherResponse: WeatherResponse?): Map<String, Double> {
        //Return value: Map<time: String, windSpeed: Double>
        return weatherResponse?.properties?.timeseries?.associate { timeSeries ->
            timeSeries.time to (timeSeries.data.instant.details["wind_speed"] ?: 0.0)
        } ?: emptyMap()
    }

    private fun getWindDirectionMap(weatherResponse: WeatherResponse?): Map<String, Double>{
        //Return value: Map<time : String, windDirection: Double>
        return weatherResponse?.properties?.timeseries?.associate {timeSeries ->
            timeSeries.time to (timeSeries.data.instant.details["wind_from_direction"] ?: 0.0)
        } ?: emptyMap()
    }

    //GETS DATA FROM: MetAlerts
    //Gets one MetAlertDataClass object from metAlertsDataSource
    private suspend fun getMetAlerts(coordinates: String) : MetAlertDataClass?{
        return metAlertsDataSource.getMetAlert(coordinates)
    }

    //GETS DATA FROM: PredefinedSpots
    private fun getPredefinedSpots(): List<PredefinedSpots> {
        return predefinedSpotsDataSource.getPredefinedSpots()
    }
}