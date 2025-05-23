package no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.predefinedSpots

import no.uio.ifi.in2000.natalan.havvarselapp.R
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.PredefinedSpots

class PredefinedSpotsDataSource {
    private val predefinedSpots: List<PredefinedSpots> = listOf(
        PredefinedSpots(
            coordinates = "58.07037852078236, 6.778011069088529",
            spotName = "Husebysanden",
            cityName = "Lista",
            optimalWindConditions = mapOf(
                "min" to 180.0,
                "max" to 270.0
            ),
            spotImage = R.drawable.husebysanden
        ),

        PredefinedSpots(
            coordinates = "59.558207, 4.60363",
            spotName = "TEST",
            cityName = "Vestland",
            optimalWindConditions = mapOf(
                "min" to 157.5,
                "max" to 247.5
            ),
            spotImage = R.drawable.husebysanden
        ),

        PredefinedSpots(
            coordinates = "58.18857641754766, 8.086584207780076",
            spotName = "Hamresanden",
            cityName = "Kristiansand",
            optimalWindConditions = mapOf(
                "min" to 157.5,
                "max" to 247.5
            ),
            spotImage = R.drawable.hamresanden
        ),



        PredefinedSpots(
            coordinates = "58.07625203318467, 7.811114127684619",
            spotName = "Høllesanden",
            cityName = "Søgne",
            optimalWindConditions = mapOf(
                "min" to 157.5,
                "max" to 247.5
            ),
            spotImage = R.drawable.hollesanden
        ),

        PredefinedSpots(
            coordinates = "58.135371306063874, 7.034859484463499",
            spotName = "Kvaviksanden",
            cityName = "Lyngdal",
            optimalWindConditions = mapOf(
                "min" to 180.0,
                "max" to 247.5
            ),
            spotImage = R.drawable.kvaviksanden
        ),
        PredefinedSpots(
            coordinates = "58.0697096704821, 6.685477207426811",
            spotName = "Kviljosanden",
            cityName = "Lista",
            optimalWindConditions = mapOf(
                "min" to 112.5,
                "max" to 292.5
            ),
            spotImage = R.drawable.kviljosanden
        ),
        PredefinedSpots(
            coordinates = "58.06814063685252, 6.731489560823652",
            spotName = "Haviksanden",
            cityName = "Lista",
            optimalWindConditions = mapOf(
                "min" to 112.5,
                "max" to 247.5
            ),
            spotImage = R.drawable.haviksanden
        )
    )
    fun getPredefinedSpots(): List<PredefinedSpots>{
        return predefinedSpots
    }
}