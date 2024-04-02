package no.uio.ifi.in2000.natalan.havvarselapp.Data.Havvarsel

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.gson.gson
import io.ktor.util.appendIfNameAbsent

class HavvarselDataSource {
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


    suspend fun getHavvarselData(): HavvarselDataClass? {
        return try {
            client.use { httpClient ->
                val response: HttpResponse = httpClient.get(endpoint)
                response.body<HavvarselDataClass>()

                }
        } catch (e: Exception) {
            e.printStackTrace()
            null
            }
        }









}
suspend fun testHavvarselDataFetching() {
    val dataSource = HavvarselDataSource()

    try {
        val havvarselData = dataSource.getHavvarselData()

        if (havvarselData != null) {
            println("Havvarsel data fetched successfully!")

            // Print some information for testing

            havvarselData.features?.forEach { feature ->
                val properties = feature.properties
                println("Properties: $properties")
                //Log.d("HAVARSEL DATA SOURCE", "$properties")

                if (properties != null) {
                    println("Title: ${properties.title}")
                    println("Description: ${properties.description}")
                    println("Severity: ${properties.severity}")
                    println("-----")
                } else {
                    println("Properties are null for a feature.")
                }





            }

        } else {
            println("Failed to fetch Havvarsel data.")
        }


    } catch (e: Exception) {
        e.printStackTrace()
    }
}

suspend fun main(){
    testHavvarselDataFetching()
}



