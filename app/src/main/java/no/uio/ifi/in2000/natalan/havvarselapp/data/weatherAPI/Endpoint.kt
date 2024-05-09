package no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI

object Endpoint {
    const val LOCATIONFORECAST: String = "https://gw-uio.intark.uh-it.no/in2000/"
    const val LF_WEATHERRESPONSE: String ="https://api.met.no/weatherapi/locationforecast/2.0/compact?"
    const val METALERT_TEST: String = "https://api.met.no/weatherapi/metalerts/2.0/test.json?"
    const val METALERT_EXAMPLE: String = "https://api.met.no/weatherapi/metalerts/2.0/example.json?"
    const val METALERT_CURRENT: String = "https://api.met.no/weatherapi/metalerts/2.0/current.json?"
}