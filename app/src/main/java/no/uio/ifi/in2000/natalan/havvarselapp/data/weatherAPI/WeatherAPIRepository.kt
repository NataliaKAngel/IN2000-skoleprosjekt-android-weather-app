package no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI

import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.locationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.metAlerts.MetAlertDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast.WeatherResponse
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertDataClass
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.Properties
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.Spot

class WeatherAPIRepository (
    private val predefinedSpotsList: List<PredefinedSpots>,
    private val locationForecastDataSource: LocationForecastDataSource,
    private val metAlertDataSource: MetAlertDataSource

){
    //Map: Stores the predefined coordinates
    private val predefinedSpotsMap = createPredefinedSpots()

    //Creates a map of predefined kite spots connected to the correct Spot-object
    private fun createPredefinedSpots(): Map<PredefinedSpots, Spot?>{
        return mapOf<PredefinedSpots, Spot?>()
    }

    //Offers the map of predefined kite spots to ViewModel
    fun getPredefinedSpots(): Map<PredefinedSpots, Spot?>{
        return predefinedSpotsMap
    }

    //LOCATIONFORECASTREPOSITORY

    // Utgangspunkt til Torsdag
    //TODO: Change arguments to this method to coordinate (comes from ViewModel)
    suspend fun getWeatherResponse(lat: String, lon: String, alt: String? = null): WeatherResponse? {
        //TODO: Uncomment this line of code when the TODO in datasource is solved
        // Holds List<WeatherResponse> from locationForecastDataSource
        // val weatherResponses: List<WeatherResponse> = locationForecastDataSource.getLocationForecast(lat, lon, alt)

        // TODO: Search through weatherResponses with built in Kotlin functions and return the WeatherResponse-object that matches the coordinates
        // Location of the coordinates:
        // coordinates: List<Double> = WeatherResponse.Geometry.coordinate
        return locationForecastDataSource.getWeatherResponse(lat, lon, alt)
    }

    // Different methods to transform the data from a WeatherResponse. Extract the wind direction etc.
    private fun getWindSpeedMap(weatherResponse: WeatherResponse): Map<String, Double> { //Return value: Map<time: String, windSpeed: Double>
        return weatherResponse.properties?.timeseries?.associate { timeSeries ->
            timeSeries.time to (timeSeries.data.instant.details["windSpeed"] ?: 0.0)
        } ?: emptyMap()
    }

    private fun getWindDirectionMap(weatherResponse: WeatherResponse): Map<String, Double>{ //Return value: Map<time : String, windDirection: Double>
        return weatherResponse.properties?.timeseries?.associate {timeSeries ->
            timeSeries.time to (timeSeries.data.instant.details["windFromDirection"] ?: 0.0)
        } ?: emptyMap()
    }

    private fun getUnitMap(weatherResponse: WeatherResponse): Map<String?, String?>? {
        return weatherResponse.properties?.meta?.units
    }

    //Not in use:
    suspend fun getWeatherResponseAirPressure(latitude: String, longitude: String, altitude: String? = null): Map<String, Double>{
        val weatherResponse = locationForecastDataSource.getWeatherResponse(latitude, longitude, altitude)
        val timeseries = weatherResponse?.properties?.timeseries

        val airPressureMap = mutableMapOf<String, Double>()

        timeseries?.forEach { timeseriesItem ->
            val time = timeseriesItem.time // Get the time for this timeseries
            val details = timeseriesItem.data.instant.details // Get the details object within instant

            // Check if details is not null and contains the wind_speed property
            if (details.containsKey("air_pressure_at_sea_level")) {
                val airpressure = details["air_pressure_at_sea_level"]// Get the wind speed value

                // If windSpeed is not null, store it in the map
                if (airpressure != null) {
                    airPressureMap[time] = airpressure
                }
            }
        }
        return airPressureMap
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



