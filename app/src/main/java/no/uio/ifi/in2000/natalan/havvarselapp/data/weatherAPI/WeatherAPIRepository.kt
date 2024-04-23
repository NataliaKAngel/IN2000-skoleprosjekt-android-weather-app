package no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI

import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.locationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.metAlerts.MetAlertsDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.predefinedSpots.PredefinedSpotsDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast.WeatherResponse
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.Feature
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertDataClass
import no.uio.ifi.in2000.natalan.havvarselapp.model.predefinedSpots.PredefinedSpots
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
            coordinates = predefinedSpot.coordinates, //The coordinates of the spot
            spotName = predefinedSpot.spotName, //The name of the spot
            cityName = predefinedSpot.cityName, //The city the spot lies in
            optimalWindConditions = predefinedSpot.optimalWindConditions, //Min and max value for wind direction
            photo = "",  //Photo of the spot as URL
            windSpeed = windSpeed, //Map<String, Double>
            windSpeedUnit = windSpeedUnit, //String
            windDirection = windDirection, //Map<String, Double>
            windDirectionUnit = windDirectionUnit, //String
            riskMatrixColor = riskMatrixColor,  //String
            description = description,  //String
            triggerLevel = triggerLevel, //String
            recommendationColor = "" //Recommended color for kiting
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



