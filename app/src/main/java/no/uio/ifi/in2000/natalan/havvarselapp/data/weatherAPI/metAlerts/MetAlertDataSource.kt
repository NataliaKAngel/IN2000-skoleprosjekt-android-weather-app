package no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.metAlerts

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import io.ktor.util.appendIfNameAbsent
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertDataClass

class MetAlertDataSource {
    // Variables holds information for API connection
    private val proxyKey = "ab4e9a8e7-469d-499e-822a-7df85483df8c"
    private val endpoint = "https://api.met.no/weatherapi/metalerts/2.0/current.json"
    private val apiKey = "X-Gravitee-API-Key"

    // Connect to IFI Proxy
    private val client = HttpClient(CIO) {
        defaultRequest {
            url(endpoint)
            headers.appendIfNameAbsent(apiKey, proxyKey)
        }

        //TODO: gson or json
        // Set up for handling JSON data and configuring the JSON serializer/deserializer
        install(ContentNegotiation) {
          gson()
        }

        /*
        //Prøvde å bytte til json, men da ble ikke data hentet fra API:

        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

         */
    }

    // Method returns a list of features that contains coordinates to a given area and properties (MetAlertDataClass)
    suspend fun getHavvarselData(): MetAlertDataClass? {
        return try {
            client.use { httpClient ->
                val response = httpClient.get(endpoint)
                response.body<MetAlertDataClass>()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        /*
        //Prøvde å skrive dette på samme form som for LocationForecast, men da ble ikke data hentet fra API
        return try {
            // Connects to the API with correct URL
            val response = client.get(endpoint)

            // Holds response body
            val metAlertResponse = response.body<MetAlertDataClass>()

            // Returns response body
            metAlertResponse
        } catch (e: Exception) {
            // Logging: Failed to connect to the API
            Log.e("MetAlertDataSource", e.printStackTrace().toString(), e)

            // Returns null
            null
        }

         */

    }
}