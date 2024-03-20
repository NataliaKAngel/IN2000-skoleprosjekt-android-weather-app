package no.uio.ifi.in2000.natalan.havvarselapp.data.locationForcast

import io.ktor.client.statement.HttpResponse

class LocationForecastRepository {
    private val locationForecastDataSrc = LocationForecastDataSource()

    suspend fun getLocationForecast(lat: String, lon: String, alt: String? = null): WeatherResponse? {
        return locationForecastDataSrc.getLocationForecast(lat, lon, alt)
    }
}