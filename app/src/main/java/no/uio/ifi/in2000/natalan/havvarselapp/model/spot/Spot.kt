package no.uio.ifi.in2000.natalan.havvarselapp.model.spot

import no.uio.ifi.in2000.natalan.havvarselapp.model.locationForecast.WeatherResponse
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.Feature
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertDataClass
import no.uio.ifi.in2000.natalan.havvarselapp.model.predefinedSpots.PredefinedSpots

data class Spot (
    val predefinedSpots: PredefinedSpots, // holds the spots coordinate, cityName, spotName and optimal wind condition.
    val alerts : List<AlertInfo>, // holds a list of all the relevant alerts for the spot (riskMatrixColor, description, event, startTime and endTime)
    val spotDetails : List<KiteSpotInfo>, // holds a list of the kiting conditions for the spot (some of the variables: date, time, windspeed, winddirection, kiteRecommendationColor)

)