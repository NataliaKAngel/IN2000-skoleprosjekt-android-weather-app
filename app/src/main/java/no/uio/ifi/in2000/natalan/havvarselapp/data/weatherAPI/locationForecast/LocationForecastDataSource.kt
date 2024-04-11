package no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.locationForecast

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.Endpoint.LOCATIONFORECAST
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.Endpoint.WEATHERRESPONSE
import no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast.WeatherResponse

class LocationForecastDataSource {
    // Variables holds information for API connection
    private val proxyKey = "ab4e9a8e7-469d-499e-822a-7df85483df8c" //MÅ FLYTTES!!!!!!
    private val endpoint = LOCATIONFORECAST
    private val apiKey = "X-Gravitee-API-Key" //MÅ FLYTTES!!!!!!!

    // Connect to IFI Proxy
    private val client = HttpClient(CIO){
        defaultRequest {
            url(endpoint)
            header(apiKey, proxyKey)
        }

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun getLocationForecast(latitude: String?, longitude: String?, altitude: String? = null): WeatherResponse? {
        // Variable holds coordinates to create URL

        var coordinates = "lat=$latitude&lon=$longitude"

        // Adds altitude to the URL
        if (altitude != null) {
            coordinates += "&altitude=$altitude"
        }

        // Logging: coordinates
        Log.d("LocationForecastDataSource", "Requesting weather data for coordinates: $coordinates")

        return try {
            // Connects to the API with correct URL (coordinates = lat + lon + alt)
            val response = client.get(WEATHERRESPONSE + coordinates)

            // Holds response body
            val weatherResponse = response.body<WeatherResponse>()

            // Returns response body
            weatherResponse
        } catch (e: Exception) {
            // Logging: Failed to connect to the API
            Log.e("LocationForecastDataSource", "Error during HTTP request for locationforecast", e)

            // Returns null
            null
        }
    }
}
