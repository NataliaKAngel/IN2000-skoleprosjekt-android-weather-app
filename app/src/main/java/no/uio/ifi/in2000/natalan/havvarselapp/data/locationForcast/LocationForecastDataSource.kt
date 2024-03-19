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

    suspend fun getLocationForecast(latitude: String, longitude: String? = null, altitude: String?): WeatherResponse? {
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
    }

}

// Hvis details er ment å være en liste, bør koden se ut noe slik:

/*suspend fun main() {
    val lfds = LocationForecastDataSource()
    val liste = lfds.getLocationForecast("68.18", "18", null)
    println(liste?.type)

    liste?.properties?.timeseries?.get(0)?.data?.instant?.details?.forEach { details ->
        println("Details: $details")
    }
}*/

// Hvis details ikke er en liste, men heller et enkelt objekt, kan du endre koden slik:
/*suspend fun main() {
    val lfds = LocationForecastDataSource()
    val liste = lfds.getLocationForecast("68.18", "18", null)
    println(liste?.type)

    val details = liste?.properties?.timeseries?.get(0)?.data?.instant?.details
    if (details != null) {
        println("Details: $details")
    }
}*/

/*suspend fun main(){
    val lfds = LocationForecastDataSource()
    val liste = lfds.getLocationForecast("68.18","18", null)
    println(liste?.type)

    liste?.properties?.timeseries?.get(0)?.data?.instant?.details?.forEach{
        println(it.key)
        println(it.value)
    }
}*/


/*suspend fun main() {
    val lfds = LocationForecastDataSource()
    val liste = lfds.getLocationForecast("68.18", "18", null)
    println(liste?.type)

    liste?.properties?.timeseries?.get(0)?.data?.instant?.details?.forEach { (key, value) ->
        println("$key: $value")
    }
}*/


