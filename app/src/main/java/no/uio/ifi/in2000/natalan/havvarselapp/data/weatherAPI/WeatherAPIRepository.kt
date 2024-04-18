package no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI

import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.locationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.metAlerts.MetAlertDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast.WeatherResponse
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertDataClass
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.Properties
import no.uio.ifi.in2000.natalan.havvarselapp.model.predefinedSpots.PredefinedSpots
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.Spot

class WeatherAPIRepository (
    private val predefinedSpotsList: List<PredefinedSpots>,
    private val locationForecastDataSource: LocationForecastDataSource,
    private val metAlertDataSource: MetAlertDataSource
){
    //Creates: Map<PredefinedSpots, Spot?>
    private suspend fun createPredefinedSpots(): Map<PredefinedSpots, Spot?>{
        return predefinedSpotsList.associateWith { predefinedSpot ->
            //Gets a new WeatherResponse based on the coordinates in the PredefinedSpots-object
            val weatherResponse = getWeatherResponse(predefinedSpot.coordinates)
            //Using let-blocks to secure that weatherResponse, windSpeed, windDirection and units is not null
            weatherResponse?.let {
                val windSpeed = getWindSpeedMap(it)
                val windDirection = getWindDirectionMap(it)
                val windSpeedUnit = getWindSpeedUnit(it)
                val windDirectionUnit = getWindDirectionUnit(it)

                //Creates one Spot-object per PredefinedSpot-object
                Spot(
                    coordinates = predefinedSpot.coordinates, //The coordinates of the spot
                    spotName = predefinedSpot.spotName, //The name of the spot
                    cityName = predefinedSpot.cityName, //The city the spot lies in
                    areaName = "",  //The name of the area the spot is a part of (from MetAlert)
                    photo = "",  //Photo of the spot as URL
                    windSpeed = windSpeed, //Map<String, Double>
                    windSpeedUnit = windSpeedUnit,
                    windDirection = windDirection, //Map<String, Double>
                    windDirectionUnit = windDirectionUnit, //String
                    riskMatrixColor = "",  // From MetAlerts
                    awarenessSeriousness = "",  // From MetAlerts
                    bestWindDirection = 0.0,  //Recommended windDirection for the spot
                    recommendationColor = "" //Recommended color for kiting
                )
            }
        }
    }

    //Creates a Map<PredefinedSpots, Spot?> and returns it. Offers the map of predefined kite spots to ViewModel
    suspend fun getPredefinedSpots(): Map<PredefinedSpots, Spot?>{
        return createPredefinedSpots()
    }

    //Returns one Spot-object based on coordinates
    suspend fun getOneSpot(coordinates: String): Spot? {
        //List of all the Spots
        val spots = createPredefinedSpots().values.toList()

        return spots.find { (it?.coordinates ?: "") == coordinates }
    }

    //LOCATIONFORECASTREPOSITORY
    //Gets one WeatherResponse object from locationForecastDataSource
    private suspend fun getWeatherResponse(coordinates: String): WeatherResponse? {
        return locationForecastDataSource.getWeatherResponse(coordinates)
    }

    // Different methods to transform the data from a WeatherResponse.
    private fun getWindSpeedMap(weatherResponse: WeatherResponse): Map<String, Double> { //Return value: Map<time: String, windSpeed: Double>
        return weatherResponse.properties?.timeseries?.associate { timeSeries ->
            timeSeries.time to (timeSeries.data.instant.details["wind_speed"] ?: 0.0)
        } ?: emptyMap()
    }

    private fun getWindDirectionMap(weatherResponse: WeatherResponse): Map<String, Double>{ //Return value: Map<time : String, windDirection: Double>
        return weatherResponse.properties?.timeseries?.associate {timeSeries ->
            timeSeries.time to (timeSeries.data.instant.details["wind_from_direction"] ?: 0.0)
        } ?: emptyMap()
    }

    private fun getWindSpeedUnit(weatherResponse: WeatherResponse): String? {
        val units = weatherResponse.properties?.meta?.units
        return units?.get("wind_speed")
    }

    private fun getWindDirectionUnit(weatherResponse: WeatherResponse): String?{
        val units = weatherResponse.properties?.meta?.units
        return units?.get("wind_from_direction")
    }

    suspend fun getProperty(coordinate: List<List<List<Any?>>>?, propertyName: String): Any? {
        // Fetch the MetAlertDataClass containing all the features
        val metAlertDataClass: MetAlertDataClass? = metAlertDataSource.getMetAlert()

        return metAlertDataClass?.features
            ?.find { feature ->
                feature.geometry.coordinates == coordinate
            }
            ?.properties
            ?.let { property ->
                when (propertyName) {
                    "awarenessSeriousness" -> property.awarenessSeriousness
                    "riskMatrixColor" -> property.riskMatrixColor
                    "description" -> property.description
                    "triggerLevel" -> property.triggerLevel
                    // Add more cases for other properties as needed
                    else -> null
                }
            }
    }


    suspend fun checkGeographicDomain(coordinate: List<List<List<Any?>>>?) : Boolean {
        val metAlertDataClass: MetAlertDataClass? = metAlertDataSource.getMetAlert()
        if (metAlertDataClass != null) {
            for (feature in metAlertDataClass.features) {
                return feature.properties.geographicDomain == "marine"
            }
        }
        return false
    }

}



