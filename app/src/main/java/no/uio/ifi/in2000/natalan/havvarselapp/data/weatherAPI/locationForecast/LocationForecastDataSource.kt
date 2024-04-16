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
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.Endpoint.LF_WEATHERRESPONSE
import no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast.WeatherResponse

class LocationForecastDataSource {
    // Variables holds information for API connection
    private val proxyKey = "ab4e9a8e7-469d-499e-822a-7df85483df8c" //MÅ FLYTTES!!!!!!
    private val apiKey = "X-Gravitee-API-Key" //MÅ FLYTTES!!!!!!!

    // Creating a client and using the apiKey and proxyKey to connect
    private val client = HttpClient(CIO){
        defaultRequest {
            url(LOCATIONFORECAST)
            header(apiKey, proxyKey)
        }

        //Installing json to deserialize the data from the API
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    //Gets one WeatherResponse-object from LocationForecast (API)
    suspend fun getWeatherResponse(coordinates: String): WeatherResponse? {
        //Creating correct URL based on the coordinates
        val details = coordinates.split(",")
        val latitude = details[0]
        val longitude = details[1]
        val coordinatesURL = "lat=$latitude&lon=$longitude"

        // Logging: coordinates
        Log.d("LocationForecastDataSource", "Requesting weather data for coordinates: $coordinates")

        return try {
            // Connects to the API with correct URL
            val response = client.get(LF_WEATHERRESPONSE + coordinatesURL)

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
