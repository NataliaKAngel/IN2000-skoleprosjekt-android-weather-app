package no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.metAlerts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.gson.gson
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendIfNameAbsent
import kotlinx.serialization.json.Json
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.Endpoint.METALERT_EXAMPLE
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.Endpoint.METALERT_TEST
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertDataClass

class MetAlertsDataSource {
    // Variables holds information for API connection
    private val proxyKey = "ab4e9a8e7-469d-499e-822a-7df85483df8c"
    private val apiKey = "X-Gravitee-API-Key"

    // Creating a client and using the apiKey and proxyKey to connect
    private val client = HttpClient(CIO) {
        defaultRequest {
            url(METALERT_TEST)
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

    // Gets one MetAlertDataClass-object from MetAlerts (API)
    suspend fun getMetAlert(coordinates: String): MetAlertDataClass? {
        //Creating correct URL based on the coordinates
        val details = coordinates.split(",")
        val latitude = details[0]
        val longitude = details[1]
        val coordinatesURL = "lat=$latitude&lon=$longitude"

        return try {
            // Connects to the API with correct URL
            val response = client.get(METALERT_EXAMPLE + coordinatesURL)
            response.body()

        } catch (e: Exception) {
            e.printStackTrace()
            // Returns null
            null
        }
    }
}