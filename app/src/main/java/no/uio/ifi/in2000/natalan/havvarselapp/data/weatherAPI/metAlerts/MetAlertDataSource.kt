package no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.metAlerts

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import io.ktor.util.appendIfNameAbsent
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.Endpoint.METALERT
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertDataClass

class MetAlertDataSource {
    // Variables holds information for API connection
    private val proxyKey = "ab4e9a8e7-469d-499e-822a-7df85483df8c"
    private val apiKey = "X-Gravitee-API-Key"

    // Connect to IFI Proxy
    private val client = HttpClient(CIO) {
        defaultRequest {
            url(METALERT)
            headers.appendIfNameAbsent(apiKey, proxyKey)
        }

        //TODO: gson or json
        // Set up for handling JSON data and configuring the JSON serializer/deserializer
        install(ContentNegotiation) {
          gson()
        }
    }

    // Method returns a list of features that contains coordinates to a given area and properties (MetAlertDataClass)
    suspend fun getMetAlert(): MetAlertDataClass? {
        return try {
            client.use { httpClient ->
                val response = httpClient.get(METALERT)
                response.body<MetAlertDataClass>()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}