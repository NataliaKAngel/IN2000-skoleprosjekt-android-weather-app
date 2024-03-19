package no.uio.ifi.in2000.natalan.havvarselapp.data.locationForcast

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class LocationForecastDataSource {
    private val client = HttpClient(CIO){
        defaultRequest {
            url("https://gw-uio.intark.uh-it.no/in2000/")
            header("X-Gravitee-API-Key", "ab4e9a8e7-469d-499e-822a-7df85483df8c")
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
    }

    /*suspend fun getLocationForecast(latitude: String?, longitude: String?, altitude: String? = null): WeatherResponse? {
        var coordinates = "lat=$latitude&lon=$longitude"
        if (altitude != null) {
            coordinates += "&altitude=$altitude"
        }
        println("weatherapi/locationforecast/2.0/complete?$coordinates")

        return try {
            client.get("https://api.met.no/weatherapi/locationforecast/2.0/complete?$coordinates").body()
        } catch (e: Exception) {
            println("Error during HTTP request for locationforecast: $e")
            null
        }
    }*/

    suspend fun getLocationForecast(latitude: String?, longitude: String?, altitude: String? = null): WeatherResponse? {
        var coordinates = "lat=$latitude&lon=$longitude"
        if (altitude != null) {
            coordinates += "&altitude=$altitude"
        }
        Log.d("LocationForecastDataSource", "Requesting weather data for coordinates: $coordinates")

        return try {
            val response = client.get("https://api.met.no/weatherapi/locationforecast/2.0/complete?$coordinates")
            val weatherResponse = response.body<WeatherResponse>()
            Log.d("LocationForecastDataSource", "Weather response: $weatherResponse")
            weatherResponse
        } catch (e: Exception) {
            Log.e("LocationForecastDataSource", "Error during HTTP request for locationforecast", e)
            null
        }
    }
}