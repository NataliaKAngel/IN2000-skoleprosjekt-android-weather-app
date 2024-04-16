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
                val units = getUnitMap(it)
                units?.let { it1 ->
                    //Creates one Spot-object per PredefinedSpot-object
                    Spot(
                        coordinates = predefinedSpot.coordinates, //The coordinates of the spot
                        spotName = predefinedSpot.spotName, //The name of the spot
                        cityName = predefinedSpot.cityName, //The city the spot lies in
                        areaName = "",  //The name of the area the spot is a part of (from MetAlert)
                        photo = "",  //Photo of the spot as URL
                        windSpeed = windSpeed, //Map<String, Double>
                        windDirection = windDirection, //Map<String, Double>
                        units = it1, //Map<String?, String?>?
                        riskMatrixColor = "",  // From MetAlerts
                        awarenessSeriousness = "",  // From MetAlerts
                        bestWindDirection = 0.0,  //Recommended windDirection for the spot
                        recommendationColor = "" //Recommended color for kiting
                    )
                }
            }
        }
    }

    //Creates a Map<PredefinedSpots, Spot?> and returns it. Offers the map of predefined kite spots to ViewModel
    suspend fun getPredefinedSpots(): Map<PredefinedSpots, Spot?>{
        return createPredefinedSpots()
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

    private fun getUnitMap(weatherResponse: WeatherResponse): Map<String?, String?>? {
        return weatherResponse.properties?.meta?.units
    }

    //METALERTREPOSITORY
    private suspend fun getAlertMap(): Map<String, List<Properties>> {
        // Holds a MetAlertDataClass that contains a list of Feature
        val metAlertDataClass: MetAlertDataClass? = metAlertDataSource.getMetAlert()

        // alertMap = MutableMap<String, MutableList<Properties>>: String = area, MutableList<Properties> = List<MetAlertDataSource.feature.properties>
        val alertMap: MutableMap<String, MutableList<Properties>> = mutableMapOf()
        metAlertDataClass?.features?.forEach { feature ->
            val area = feature.properties.area
            if (area.isNotBlank()) {
                alertMap.getOrPut(area) { mutableListOf() }.add(feature.properties)
            }
        }
        return alertMap
    }

    //TODO: Write comments to explain this method
    suspend fun getAlertsForArea(area: String): List<Properties>? {
        val alertMap = getAlertMap()
        return alertMap[area]
    }

    //TODO: Write comments to explain this method
    suspend fun getCoordinates(): List<List<List<Any?>>>? {
        val metAlertDataClass: MetAlertDataClass? = metAlertDataSource.getMetAlert()
        return metAlertDataClass?.features?.map { feature ->
            feature.geometry.coordinates
        }
    }

    //TODO: Write method
    suspend fun getRiskMatrixColor(areaName : String): String?{

        //The variable holds a MetAlertDataClass that contains a list of Feature
        val metAlertDataClass : MetAlertDataClass? = metAlertDataSource.getMetAlert()

        // checking if metAlertDataClass is null or areaName is blank
        if(metAlertDataClass == null || areaName.isBlank()){
            return null
        }

        // finding the feature corresponding to the specific area, if the area is not found, return null
        val feature = metAlertDataClass.features.find{it.properties.area == areaName} ?: return null

        // returning the risk matrix color for the specific area
        return feature.properties.riskMatrixColor
    }

    //TODO: Write method
    // The method fetches the awarness seriousness for a specific area
    suspend fun getAwarenessSeriousness(areaName : String): String?{

        //The variable holds a MetAlertDataClass that contains a list of Feature
        val metAlertDataClass : MetAlertDataClass? = metAlertDataSource.getMetAlert()

        // checking if metAlertDataClass is null or areaName is blank
        if(metAlertDataClass == null || areaName.isBlank()){
            return null
        }
        // finding the feature corresponding to the specific area, if the area is not found, return null
        val feature = metAlertDataClass.features.find{it.properties.area == areaName} ?: return null

        // returning the awarness seriousness for the specific area
        return feature.properties.awarenessSeriousness
    }

    // a method that fetches the name of a specific area from MetAlertDataClass
    //USIKKER PÅ OM DET ER SÅNN VI VIL HENTE NAVNET TIL AREA PÅ!!
    suspend fun getAreaName(areaIndex : Int): String?{
        // This variable holds a MetAlertDataClass that contains a list of Feature
        val metAlertDataClass : MetAlertDataClass? = metAlertDataSource.getMetAlert()

        // if metAlertDataClass i null or the areaIndex is out of bounds, return null
        if(metAlertDataClass == null || areaIndex < 0 || areaIndex > metAlertDataClass.features.size){
            return null
        }

        // getting the feature based on a specific index

        val feature = metAlertDataClass.features[areaIndex]

        // returning the area name from the specific feature
        return feature.properties.area
    }

}



