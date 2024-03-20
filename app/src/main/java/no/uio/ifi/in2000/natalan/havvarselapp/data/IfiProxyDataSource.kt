package no.uio.ifi.in2000.natalan.havvarselapp.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.util.appendIfNameAbsent

class IfiProxyDataSource {

    private val client = HttpClient() {
        defaultRequest {
            url("https://gw-uio.intark.uh-it.no/in2000/")
            headers.appendIfNameAbsent("X-Gravitee-API-Key", "b4e9a8e7-469d-499e-822a-7df85483df8c")
        }
    }

    suspend fun fetchIfiProxy(): HttpResponse {
        return client.get("weatherapi/")
    }

    suspend fun fetchProjectionVariables(): List<String> {
        val response: HttpResponse = client.get("https://api.havvarsel.no/apis/duapi/havvarsel/v2/dataprojectionvariables")
        return response.bodyAsText().split(",")
    }

    suspend fun fetchAllVariables(): List<String> {
        val response: HttpResponse = client.get("https://api.havvarsel.no/apis/duapi/havvarsel/v2/variables")
        return response.bodyAsText().split(",")
    }

    suspend fun fetchDepths(): List<Double> {
        val response: HttpResponse = client.get("https://api.havvarsel.no/apis/duapi/havvarsel/v2/depths")
        return response.bodyAsText().split(",").map { it.toDouble() }
    }

    suspend fun fetchTimes(): List<String> {
        val response: HttpResponse = client.get("https://api.havvarsel.no/apis/duapi/havvarsel/v2/times")
        return response.bodyAsText().split(",")
    }

    suspend fun fetchTemperatureProjection(lon: Double, lat: Double): Map<String, Double> {
        val response: HttpResponse = client.get("https://api.havvarsel.no/apis/duapi/havvarsel/v2/temperatureprojection/$lon/$lat")
        // Split the response body by commas and create a map
        return response.bodyAsText().split(",").associate {
            val (key, value) = it.split("=") // Split each key-value pair by '='
            key to value.toDouble()          // Convert value to Double
        }
    }

    suspend fun fetchWindAndCurrentProjection(lon1: Double, lat1: Double, lon2: Double, lat2: Double, time: String): Map<String, Double> {
        val response: HttpResponse = client.get("https://api.havvarsel.no/apis/duapi/havvarsel/v2/windandcurrentprojection/$lon1/$lat1/$lon2/$lat2/$time")
        // Split the response body by commas and create a map
        return response.bodyAsText().split(",").associate {
            val (key, value) = it.split("=") // Split each key-value pair by '='
            key to value.toDouble()          // Convert value to Double
        }
    }

}