package no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI

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
import java.time.LocalTime
import java.util.Locale

class WeatherAPIRepository (
    private val predefinedSpotsDataSource: PredefinedSpotsDataSource,
    private val locationForecastDataSource: LocationForecastDataSource,
    private val metAlertsDataSource: MetAlertsDataSource
){
    //METHODS TO CREATE OBJECTS OR TRANSFORM DATA: Helping methods
    //Creates: List<Spot>
    private suspend fun createAllSpots(): List<Spot>{
        return getPredefinedSpots().map { predefinedSpot ->
            createOneSpot(
                predefinedSpot,
                getWeatherResponse(predefinedSpot.coordinates),
                getMetAlerts(predefinedSpot.coordinates)?.features
            )
        }
    }

    private fun createOneSpot(predefinedSpot: PredefinedSpots, weatherResponse: WeatherResponse?, features: List<Feature>?): Spot {
        //Gets data from LocationForecast-API
        val windSpeed = getWindSpeedMap(weatherResponse)
        val windDirection = getWindDirectionMap(weatherResponse)
        val windSpeedUnit = getWindSpeedUnit(weatherResponse)
        val windDirectionUnit = getWindDirectionUnit(weatherResponse)
        val alerts = createAllAlertInfos(features)

        //Creates and returns one Spot-object
        return Spot(
            predefinedSpot = predefinedSpot,
            alerts = alerts,
            spotDetails = createAllSpotInfos(
                alerts,
                windSpeed,
                windDirection,
                windSpeedUnit,
                windDirectionUnit,
                predefinedSpot.optimalWindConditions)
        )
    }

    private fun createAllAlertInfos(features: List<Feature>?): List<AlertInfo?> {
        return features?.map { createAlertInfo(it) } ?: emptyList()
    }

    private fun createAlertInfo(feature: Feature?): AlertInfo {
        return AlertInfo(
            riskMatrixColor = feature?.properties?.riskMatrixColor,
            description = feature?.properties?.description,
            event = feature?.properties?.event,
            startTime = "",
            endTime = feature?.properties?.eventEndingTime
        )
    }

    private fun createAllSpotInfos(alerts: List<AlertInfo?>, windSpeed: Map<String, Double>, windDirection: Map<String, Double>, windSpeedUnit: String?, windDirectionUnit: String?, optimalWindConditions: Map<String, Double>): List<SpotInfo> {
        return windSpeed.keys.map { timeStamp ->
            val (date, time) = timeStamp.split("T")
            val windSpeedValue = windSpeed[timeStamp]
            val windDirectionValue = windDirection[timeStamp]
            val color = calculateKiteRecommendation(alerts, windSpeedValue, windDirectionValue, optimalWindConditions, timeStamp)
            SpotInfo(
                date = transformDate(date),
                time = transformTime(time),
                windSpeedValue = windSpeedValue,
                windSpeedUnit = windSpeedUnit,
                windDirectionValue = windDirectionValue,
                windDirectionUnit = windDirectionUnit,
                windDirectionString = windDirectionValue?.let { transformWindDirection(it) },
                kiteRecommendationSmallThumb = getSmallThumb(color),
                kiteRecommendationBigThumb = getBigThumb(color)
            )
        }
    }

    private fun transformDate(date: String): String {
        val (year, month, day) = date.split("-")

        val dateString = day + (when (month){
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
        return dateString
    }

    private fun transformTime(time: String): String {
        val (hour, minutes, seconds) = time.split(":")

        return "$hour.$minutes"
    }

    private fun transformWindDirection(windDirectionValue: Double): String {
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
        if (alert?.endTime == null){
            return false
        }
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
        //val startTime = alert.startTime.let { format.parse(it) }
        val endTime = alert.endTime.let { format.parse(it) }
        val timeToCheck = format.parse(timeStamp)

        if (timeToCheck != null) {
            return timeToCheck.before(endTime)
        }
        return false
    }

    private fun calculateWindDirection(windDirectionValue: Double?, optimalWindConditions: Map<String, Double>): String{
        val minWind = optimalWindConditions["min"] ?: 0.0
        val maxWind = optimalWindConditions["max"] ?: 360.0

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

    //OFFERS UI-STATE DATA TO: ViewModel
    //Creates a list of Spot-objects and returns it.
    suspend fun getAllSpots(): List<Spot>{
        return createAllSpots()
    }

    //Returns one Spot-object based on coordinates to ViewModel
    suspend fun getOneSpot(coordinates: String): Spot {
        //Getting predefinedSpot, WeatherResponse and Feature to create Spot-object
        val predefinedSpot = getPredefinedSpots().find { it.coordinates == coordinates }
        val weatherResponse = getWeatherResponse(coordinates)
        val feature = getMetAlerts(coordinates)?.features

        return createOneSpot(predefinedSpot!!, weatherResponse, feature)
    }

    //Returns: Map<String, Int>. Offers a map with a small thumb icon per spot.
    fun getThumbs(spots: List<Spot>) : Map<String, Int>{
        return spots.associate { spot ->
            spot.predefinedSpot.coordinates to getCorrectThumbIcon(spot)
        }
    }

    private fun getCorrectThumbIcon(spot: Spot) : Int{
        val currentTime = LocalTime.now()
        val hour = currentTime.hour.toString()

        spot.spotDetails.forEach { spotInfo ->
            val spotTime = spotInfo.time.split(".")
            if (spotTime[0] == hour){
                return spotInfo.kiteRecommendationSmallThumb
            }
        }
        return R.drawable.sgreythumb
    }

    //GETS AND TRANSFORM DATA FROM: LocationForecast
    //Gets one WeatherResponse object from locationForecastDataSource
    private suspend fun getWeatherResponse(coordinates: String): WeatherResponse? {
        return locationForecastDataSource.getWeatherResponse(coordinates)
    }

    // Different methods to transform the data from a WeatherResponse.
    private fun getWindSpeedMap(weatherResponse: WeatherResponse?): Map<String, Double> { //Return value: Map<time: String, windSpeed: Double>
        return weatherResponse?.properties?.timeseries?.associate { timeSeries ->
            timeSeries.time to (timeSeries.data.instant.details["wind_speed"] ?: 0.0)
        } ?: emptyMap()
    }

    private fun getWindDirectionMap(weatherResponse: WeatherResponse?): Map<String, Double>{ //Return value: Map<time : String, windDirection: Double>
        return weatherResponse?.properties?.timeseries?.associate {timeSeries ->
            timeSeries.time to (timeSeries.data.instant.details["wind_from_direction"] ?: 0.0)
        } ?: emptyMap()
    }

    private fun getWindSpeedUnit(weatherResponse: WeatherResponse?): String? {
        val units = weatherResponse?.properties?.meta?.units
        return units?.get("wind_speed")
    }

    private fun getWindDirectionUnit(weatherResponse: WeatherResponse?): String?{
        val units = weatherResponse?.properties?.meta?.units
        return units?.get("wind_from_direction")
    }

    //GETS AND TRANSFORM DATA FROM: MetAlerts
    //Gets one MetAlertDataClass object from metAlertsDataSource
    private suspend fun getMetAlerts(coordinates: String) : MetAlertDataClass?{
        return metAlertsDataSource.getMetAlert(coordinates)
    }

    //GETS DATA FROM: PredefinedSpots
    private fun getPredefinedSpots(): List<PredefinedSpots> {
        return predefinedSpotsDataSource.getPredefinedSpots()
    }
}



