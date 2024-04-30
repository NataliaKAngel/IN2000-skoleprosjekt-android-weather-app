package no.uio.ifi.in2000.natalan.havvarselapp.UnitTest

import org.junit.Assert
import org.junit.Test
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository

class KiteRecommendationUnitTest {
    data class Feature(
        val geometry: Geometry,
        val properties: Properties,
        val type: String,
        val whenField: When
    )

    data class Geometry(
        val coordinates: List<List<List<Double>>>,
        val type: String = "default"
    )

    data class Properties(
        val riskMatrixColor: String,
        val description: String,
        val event: String
    )

    data class When(
        val interval: List<String>
    )

    data class AlertInfo(
        val riskMatrixColor: String,
        val description: String,
        val event: String,
        val startTime: String,
        val endTime: String
    )

    @Test
    fun testCreateAllAlertsInfos() {

        val features = listOf(
            Feature(
                geometry = Geometry(listOf(listOf(listOf(1.0, 2.0)))),
                properties = Properties(
                    riskMatrixColor = "color1",
                    description = "desc1",
                    event = "event1",
                    //whenField = When(listOf("2024-04-23T10:00:00Z", "2024-04-23T12:00:00Z"))
                ),
                type = "type1",
                whenField = When(listOf("2024-04-23T10:00:00Z", "2024-04-23T12:00:00Z"))
            ),
            Feature(
                geometry = Geometry(listOf(listOf(listOf(3.0, 4.0)))),
                properties = Properties(
                    riskMatrixColor = "color2",
                    description = "desc2",
                    event = "event2",
                    //whenField = When(listOf("2024-04-23T13:00:00Z", "2024-04-23T15:00:00Z"))
                ),
                type = "type2",
                whenField = When(listOf("2024-04-23T13:00:00Z", "2024-04-23T15:00:00Z"))
            )
        )

        val expected = listOf(
            AlertInfo("color1", "desc1", "event1", "2024-04-23T10:00:00Z", "2024-04-23T12:00:00Z"),
            AlertInfo("color2", "desc2", "event2", "2024-04-23T13:00:00Z", "2024-04-23T15:00:00Z")
        )
        Assert.assertEquals(expected, createAllAlertInfos(features))
    }

    @Test
    fun testTransformDate() {
        // Input date
        val inputDate = "2024-04-30"

        // Expected output
        val expectedOutput = "30. April"

        // Call the function
        val actualOutput = transformDate(inputDate)

        // Assert
        Assert.assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun testTransformTime(){
        // Input time
        val inputTime = "08:30:45"

        // Expected output
        val expectedOutput = "08.30"

        // Call the function
        val actualOutput = transformTime(inputTime)

        // Assert
        Assert.assertEquals(expectedOutput, actualOutput)
    }

}


    private fun createAllAlertInfos(features: List<KiteRecommendationUnitTest.Feature>?): List<KiteRecommendationUnitTest.AlertInfo> {
        return features?.map { createAlertInfo(it) } ?: emptyList()
    }

    private fun createAlertInfo(feature: KiteRecommendationUnitTest.Feature): KiteRecommendationUnitTest.AlertInfo {
        return KiteRecommendationUnitTest.AlertInfo(
            riskMatrixColor = feature.properties.riskMatrixColor,
            description = feature.properties.description,
            event = feature.properties.event,
            startTime = feature.whenField.interval[0],
            endTime = feature.whenField.interval[1]
        )
    }
    private fun transformDate(date: String): String {
        val (year, month, day) = date.split("-")

        val dateString = day + (when (month){
            "01" -> ". Januar"
            "02" -> ". Februar"
            "03" -> ". Mars"
            "04" -> ". April"
            "05" -> ". Mai"
            "06" -> ". Juni"
            "07" -> ". Juli"
            "08" -> ". August"
            "09" -> ". September"
            "10" -> ". Oktober"
            "11" -> ". November"
            "12" -> ". Desember"
            else -> ""
        })
        return dateString
    }
    private fun transformTime(time: String): String {
        val (hour, minutes, seconds) = time.split(":")

        return "$hour.$minutes"
    }
