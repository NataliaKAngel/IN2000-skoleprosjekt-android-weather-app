package no.uio.ifi.in2000.natalan.havvarselapp.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.call.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.util.appendIfNameAbsent

class MetAlertsDataSource {

    private val client = HttpClient() {
        defaultRequest {
            url("https://gw-uio.intark.uh-it.no/in2000/")
            headers.appendIfNameAbsent("X-Gravitee-API-Key", "b4e9a8e7-469d-499e-822a-7df85483df8c")
        }
    }

    suspend fun fetchMetAlerts(): HttpResponse {
        val partiesUrl = "weatherapi/"
        val results = client.get(partiesUrl)
        return results
    }



}