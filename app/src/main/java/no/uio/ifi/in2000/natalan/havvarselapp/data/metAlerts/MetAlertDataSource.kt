package no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.gson.gson
import io.ktor.util.appendIfNameAbsent
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertDataClass

class MetAlertDataSource {
    private val proxyKey = "ab4e9a8e7-469d-499e-822a-7df85483df8c"
    private val endpoint = "https://api.met.no/weatherapi/metalerts/2.0/current.json"
    private val client = HttpClient() {
        defaultRequest {
            url(endpoint)
            headers.appendIfNameAbsent("X-Gravitee-API-Key", proxyKey)
            }
        install(ContentNegotiation) {
            gson()
            }
        }

    suspend fun getHavvarselData(): MetAlertDataClass? {
        return try {
            client.use { httpClient ->
                val response: HttpResponse = httpClient.get(endpoint)
                response.body<MetAlertDataClass>()

            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}