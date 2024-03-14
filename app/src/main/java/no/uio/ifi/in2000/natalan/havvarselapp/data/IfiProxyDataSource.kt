package no.uio.ifi.in2000.natalan.havvarselapp.data

import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.util.appendIfNameAbsent

class IfiProxyDataSource {

    private val client = HttpClient() {
        defaultRequest {
            url("https://gw-uio.intark.uh-it.no/in2000/")
            headers.appendIfNameAbsent("X-Gravitee-API-Key", "ab4e9a8e7-469d-499e-822a-7df85483df8c")
        }
    }

    suspend fun fetchIfiProxy(): HttpResponse {
        val partiesUrl = "weatherapi/"
        val results = client.get(partiesUrl)
        return results
    }



}