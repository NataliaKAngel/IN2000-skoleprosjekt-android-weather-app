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

        // TODO: Search through weatherResponses with built in Kotlin functions and return the WeatherResponse-object that matches the coordinates
        // Location of the coordinates:
        // coordinates: List<Double> = WeatherResponse.Geometry.coordinate
        return locationForecastDataSource.getLocationForecast(lat, lon, alt)
    }

    // Different methods to transform the data from a WeatherResponse.
    // Extract the wind direction etc.

    suspend fun getWeatherResponseUnit(latitude: String, longitude: String, altitude: String? = null): Map<String?, String?>? {
        val weatherResponse = locationForecastDataSource.getLocationForecast(latitude, longitude, altitude)
        return weatherResponse?.properties?.meta?.units
    }

    suspend fun getWeatherResponseWindSpeedMap(latitude: String, longitude: String, altitude: String? = null): Map<String, Double> {
        val weatherResponse = locationForecastDataSource.getLocationForecast(latitude, longitude, altitude)
        val timeseries = weatherResponse?.properties?.timeseries

        val windSpeedMap = mutableMapOf<String, Double>()

        timeseries?.forEach { timeseriesItem ->
            val time = timeseriesItem.time // Get the time for this timeseries
            val details = timeseriesItem.data.instant.details // Get the details object within instant

            // Check if details is not null and contains the wind_speed property
            if (details.containsKey("wind_speed")) {
                val windSpeed = details["wind_speed"] as? Double // Get the wind speed value

                // If windSpeed is not null, store it in the map
                if (windSpeed != null) {
                    windSpeedMap[time] = windSpeed
                }
            }
        }
        return windSpeedMap
    }

    suspend fun getWeatherResponseAirTemperature(latitude: String, longitude: String, altitude: String? = null): Map<String, Double>{
        val weatherResponse = locationForecastDataSource.getLocationForecast(latitude, longitude, altitude)
        val timeseries = weatherResponse?.properties?.timeseries

        val air_tempMap = mutableMapOf<String, Double>()

        timeseries?.forEach { timeseriesItem ->
            val time = timeseriesItem.time // Get the time for this timeseries
            val details = timeseriesItem.data.instant.details // Get the details object within instant

            // Check if details is not null and contains the wind_speed property
            if (details.containsKey("air_temperature")) {
                val air_tempSpeed = details["air_temperature"] as? Double // Get the wind speed value

                // If windSpeed is not null, store it in the map
                if (air_tempSpeed != null) {
                    air_tempMap[time] = air_tempSpeed
                }
            }
        }
        return air_tempMap
    }

    suspend fun getWeatherResponseAirPressure(latitude: String, longitude: String, altitude: String? = null): Map<String, Double>{
        val weatherResponse = locationForecastDataSource.getLocationForecast(latitude, longitude, altitude)
        val timeseries = weatherResponse?.properties?.timeseries

        val air_pressureMap = mutableMapOf<String, Double>()

        timeseries?.forEach { timeseriesItem ->
            val time = timeseriesItem.time // Get the time for this timeseries
            val details = timeseriesItem.data.instant.details // Get the details object within instant

            // Check if details is not null and contains the wind_speed property
            if (details.containsKey("air_pressure_at_sea_level")) {
                val airpressure = details["air_pressure_at_sea_level"] as? Double // Get the wind speed value

                // If windSpeed is not null, store it in the map
                if (airpressure != null) {
                    air_pressureMap[time] = airpressure
                }
            }
        }
        return air_pressureMap
    }

    suspend fun getWeatherResponseWindDirection(latitude: String, longitude: String, altitude: String? = null): Map<String, Double>{
        val weatherResponse = locationForecastDataSource.getLocationForecast(latitude, longitude, altitude)
        val timeseries = weatherResponse?.properties?.timeseries

        val windDirectionMap = mutableMapOf<String, Double>()

        timeseries?.forEach { timeseriesItem ->
            val time = timeseriesItem.time // Get the time for this timeseries
            val details = timeseriesItem.data.instant.details // Get the details object within instant

            // Check if details is not null and contains the wind_speed property
            if (details.containsKey("wind_from_direction")) {
                val windDirection = details["wind_from_direction"] as? Double // Get the wind speed value

                // If windSpeed is not null, store it in the map
                if (windDirection != null) {
                    windDirectionMap[time] = windDirection
                }
            }
        }
        return windDirectionMap
    }
}