package no.uio.ifi.in2000.natalan.havvarselapp.data.locationForecast

import no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast.WeatherResponse

class LocationForecastRepository (
    private val locationForecastDataSource: LocationForecastDataSource
){

    //TODO: Change arguments to this method to coordinate (comes from ViewModel)
    suspend fun getLocationForecast(lat: String, lon: String, alt: String? = null): WeatherResponse? {
        //TODO: Uncomment this line of code when the TODO in datasource is solved
        // Holds List<WeatherResponse> from locationForecastDataSource
        // val weatherResponses: List<WeatherResponse> = locationForecastDataSource.getLocationForecast(lat, lon, alt)

        // TODO: Search through weatherResponses with built in Kotlin functions and retrn the WatherResponse-object that matches the coordinates
        // Location of the coordinates:
        // coordinates: List<Double> = WeatherResponse.Geometry.coordinate
        return locationForecastDataSource.getLocationForecast(lat, lon, alt)
    }

    // Different methods to transform the data from a WeatherResponse.
    // Extract the wind direction etc.

    //TODO: Method that returns WeatherResponse.properties.meta.units
    fun getWeatherResponseUnit(): Map<String?, String?>{
        return emptyMap()
    }

    //TODO: Method that returns WeatherResponse.properties.timeseries.data.instand.details
    fun getWeatherResponseWindSpeed(): Map<String?, Double>{
        return emptyMap()
    }

    //TODO: Method that returns WeatherResponse.properties.timeseries.data.instand.details
    fun getWeatherResponseAirTemperature(): Map<String?, Double>{
        return emptyMap()
    }

    //TODO: Method that returns WeatherResponse.properties.timeseries.data.instand.details
    fun getWeatherResponseAirPressure(): Map<String?, Double>{
        return emptyMap()
    }

    //TODO: Method that returns WeatherResponse.properties.timeseries.data.instand.details
    fun getWeatherResponseWindDirection(): Map<String?, Double>{
        return emptyMap()
    }
}