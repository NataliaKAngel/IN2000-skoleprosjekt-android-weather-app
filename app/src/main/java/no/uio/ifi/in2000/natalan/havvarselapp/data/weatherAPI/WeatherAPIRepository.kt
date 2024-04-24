package no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI

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

class WeatherAPIRepository (
    private val predefinedSpotsDataSource: PredefinedSpotsDataSource,
    private val locationForecastDataSource: LocationForecastDataSource,
    private val metAlertsDataSource: MetAlertsDataSource
){
    //METHODS TO CREATE OBJECTS OR TRANSFORM DATA: Helping methods
    //Creates: Map<PredefinedSpots, Spot?>
    private suspend fun createAllSpots(): List<Spot>{
        return predefinedSpotsDataSource.getPredefinedSpots().map { predefinedSpot ->
            createOneSpot(
                predefinedSpot,
                getWeatherResponse(predefinedSpot.coordinates),
                getMetAlerts(predefinedSpot.coordinates)?.features
            )
        }
    }

    private fun createOneSpot(predefinedSpot: PredefinedSpots, weatherResponse: WeatherResponse?, features: List<Feature>?): Spot{
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

    private fun createAllAlertInfos(features: List<Feature>?): List<AlertInfo> {
        return features?.map { createAlertInfo(it) } ?: emptyList()
    }

    private fun createAlertInfo(feature: Feature): AlertInfo {
        return AlertInfo(
            riskMatrixColor = feature.properties.riskMatrixColor,
            description = feature.properties.description,
            event = feature.properties.event,
            startTime = feature.whenField.interval[0],
            endTime = feature.whenField.interval[1]
        )
    }

    private fun createAllSpotInfos(alerts: List<AlertInfo>, windSpeed: Map<String, Double>, windDirection: Map<String, Double>, windSpeedUnit: String?, windDirectionUnit: String?, optimalWindConditions: Map<String, Double>): List<SpotInfo> {
        return windSpeed.keys.map { timeStamp ->
            val (date, time) = timeStamp.split("T")
            val windSpeedValue = windSpeed[timeStamp]
            val windDirectionValue = windDirection[timeStamp]
            SpotInfo(
                date = transformDate(date),
                time = transformTime(time),
                windSpeedValue = windSpeedValue,
                windSpeedUnit = windSpeedUnit,
                windDirectionValue = windDirectionValue,
                windDirectionUnit = windDirectionUnit,
                windDirectionString = transformWindDirection(windDirectionValue),
                kiteRecommendationColor = calculateKiteRecommendation(alerts, windSpeedValue, windDirectionValue, optimalWindConditions, timeStamp)
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
        TODO("Not yet implemented")
    }

    private fun transformWindDirection(windDirectionVal: Double?): String {
        TODO("Not yet implemented")
    }

    private fun calculateKiteRecommendation(alerts: List<AlertInfo>, windSpeedVal: Double?, windDirectionVal: Double?, optimalWindConditions: Map<String, Double>, timeStamp: String): String {
        TODO("Not yet implemented")
    }

    //OFFERS SPOT-OBJECTS TO: ViewModel
    //Creates a list of Spot-objects and returns it.
    suspend fun getAllSpots(): List<Spot>{
        return createAllSpots()
    }

    //Returns one Spot-object based on coordinates to ViewModel
    suspend fun getOneSpot(coordinates: String): Spot? {
        //Getting predefinedSpot, WeatherResponse and Feature to create Spot-object
        val predefinedSpot = getPredefinedSpots().find { it.coordinates == coordinates }
        val weatherResponse = getWeatherResponse(coordinates)
        val feature = getMetAlerts(coordinates)?.features

        return predefinedSpot?.let { createOneSpot(it, weatherResponse, feature) }
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



