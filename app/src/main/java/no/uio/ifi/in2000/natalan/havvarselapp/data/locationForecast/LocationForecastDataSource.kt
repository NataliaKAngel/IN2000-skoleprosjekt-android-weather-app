package no.uio.ifi.in2000.natalan.havvarselapp.data.locationForecast

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
import no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast.WeatherResponse

class LocationForecastDataSource {
    // Variables holds information for API connection
    private val proxyKey = "ab4e9a8e7-469d-499e-822a-7df85483df8c"
    private val endpoint = "https://gw-uio.intark.uh-it.no/in2000/"
    private val apiKey = "X-Gravitee-API-Key"

    // Connect to IFI Proxy
    private val client = HttpClient(CIO){
        defaultRequest {
            url(endpoint)
            header(apiKey, proxyKey)
        }

        //TODO: gson or json
        // Set up for handling JSON data and configuring the JSON serializer/deserializer
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    // TODO: Change this method to get ALL the info from the API, not just one coordinate at the time
    // Return value: List<WeatherResponse>
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
            val response = client.get("https://api.met.no/weatherapi/locationforecast/2.0/complete?$coordinates")

            // Holds response body
            val weatherResponse = response.body<WeatherResponse>()

            // Logging: WheatherResponse
            Log.d("LocationForecastDataSource", "Weather response: $weatherResponse")

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
