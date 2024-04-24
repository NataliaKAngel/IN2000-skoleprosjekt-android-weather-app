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
    //Creates: Map<PredefinedSpots, Spot?>
    private suspend fun createAllSpots(): Map<PredefinedSpots, Spot?>{
        return predefinedSpotsDataSource.getPredefinedSpots().associateWith { predefinedSpot ->
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

        //Gets data from MetAlerts-API
        var riskMatrixColor = ""
        var description = ""
        var triggerLevel = ""

        if (!features.isNullOrEmpty()){
            val feature = features[0]
            riskMatrixColor = feature.properties.riskMatrixColor
            description = feature.properties.description
            triggerLevel = feature.properties.triggerLevel
        }


        //Creates and returns one Spot-object
        return Spot(
            predefinedSpots = predefinedSpot,
            alerts = alerts,
            spotDetails = createAllSpotInfos(alerts, windSpeed, windDirection, windSpeedUnit, windDirectionUnit, predefinedSpot.optimalWindConditions)
        )
    }

    private fun createAllSpotInfos(alerts: List<AlertInfo>, windSpeed: Map<String, Double>, windDirection: Map<String, Double>, windSpeedUnit: String?, windDirectionUnit: String?, optimalWindConditions: Map<String, Double>): List<SpotInfo> {
        return windSpeed.keys.map { timeStamp ->
            val (date, time) = timeStamp.split("T")
            val windSpeedVal = windSpeed[timeStamp].toString()
            val windDirectionVal = windDirection[timeStamp]
            SpotInfo(
                date = transformDate(date),
                time = transformTime(time),
                windSpeed = windSpeedVal,
                windSpeedUnit = windSpeedUnit,
                windDirectionDegree = windDirectionVal,
                windDirectionUnit = windDirectionUnit,
                windDirectionString = transformWindDirection(windDirectionVal),
                kiteRecommendationColor = calculateKiteRecommendation(alerts, windSpeedVal, windDirectionVal, optimalWindConditions, timeStamp)
            )
        }

    }

    private fun calculateKiteRecommendation(alerts: List<AlertInfo>, windSpeedVal: String, windDirectionVal: Double?, optimalWindConditions: Map<String, Double>, timeStamp: String): String {

    }

    private fun transformWindDirection(windDirectionVal: Double?): String {
        TODO("Not yet implemented")
    }

    private fun transformTime(time: String): String {

    }

    private fun transformDate(date: String): String {
        TODO("Not yet implemented")
    }

    private fun createAllAlertInfos(features: List<Feature>?): List<AlertInfo> {
        return features?.map { createAlertInfo(it) } ?: emptyList()
    }

    private fun createAlertInfo(feature: Feature): AlertInfo {
        return AlertInfo(
            riskMatrixColor = feature.properties.riskMatrixColor,
            description = feature.properties.description,
            event = feature.properties.event,
            startTime = feature.whenField.interval.get(0),
            endTime = feature.whenField.interval.get(1)
        )
    }


    //Creates a Map<PredefinedSpots, Spot?> and returns it. Offers the map of predefined kite spots to ViewModel
    suspend fun getAllSpots(): Map<PredefinedSpots, Spot?>{
        return createAllSpots()
    }

    //Returns one Spot-object based on coordinates to ViewModel
    suspend fun getOneSpot(coordinates: String): Spot? {
        //Getting predefinedSpot, WeatherResponse and Feature to create Spot-object
        val predefinedSpot = predefinedSpotsDataSource.getPredefinedSpots().find { it.coordinates == coordinates }
        val weatherResponse = getWeatherResponse(coordinates)
        val feature = getMetAlerts(coordinates)?.features

        return predefinedSpot?.let { createOneSpot(it, weatherResponse, feature) }
    }

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

    private suspend fun getMetAlerts(coordinates: String) : MetAlertDataClass?{
        return metAlertsDataSource.getMetAlert(coordinates)
    }
}



