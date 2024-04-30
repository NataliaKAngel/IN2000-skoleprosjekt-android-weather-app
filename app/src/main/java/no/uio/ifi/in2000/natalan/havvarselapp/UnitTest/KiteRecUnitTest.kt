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

    @Test
    fun testTransformWindDirection() {
        // Test cases with input wind direction values
        val testCases = mapOf(
            0.0 to "nord",
            45.0 to "nordøst",
            90.0 to "øst",
            135.0 to "sørøst",
            180.0 to "sør",
            225.0 to "sørvest",
            270.0 to "vest",
            315.0 to "nordvest",
            360.0 to "nord"
        )

        // Iterate through test cases
        testCases.forEach { (inputValue, expectedOutput) ->
            // Call the function for each test case
            val actualOutput = transformWindDirection(inputValue)

            // Assert
            Assert.assertEquals(expectedOutput, actualOutput)
        }
    }

    @Test
    fun testCalculateWindDirection() {
        // Test cases with input parameters
        val testCases = listOf(
            // Test case 1: Wind direction value is lower than min
            Triple(150.0, mapOf("min" to 180.0, "max" to 270.0), "low"),
            // Test case 2: Wind direction value is higher than max
            Triple(300.0, mapOf("min" to 180.0, "max" to 270.0), "high"),
            // Test case 3: Wind direction value is within min and max
            Triple(220.0, mapOf("min" to 180.0, "max" to 270.0), "correct"),
            // Test case 4: Wind direction value is null
            Triple(null, mapOf("min" to 180.0, "max" to 270.0), "unknown")
        )

        // Iterate through test cases
        testCases.forEachIndexed { index, (windDirectionValue, optimalWindConditions, expectedOutput) ->
            // Call the function for each test case
            val actualOutput = calculateWindDirection(windDirectionValue, optimalWindConditions)

            // Assert
            Assert.assertEquals("Test case ${index + 1} failed", expectedOutput, actualOutput)
        }
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
    private fun transformWindDirection(windDirectionValue: Double): String {
        return when (windDirectionValue) {
            in 337.5..360.0, in 0.0..22.5 -> "nord"
            in 22.5..67.5 -> "nordøst"
            in 67.5..112.5 -> "øst"
            in 112.5..157.5 -> "sørøst"
            in 157.5..202.5 -> "sør"
            in 202.5..247.5 -> "sørvest"
            in 247.5..292.5 -> "vest"
            in 292.5..337.5 -> "nordvest"
            else -> "Ugyldige grader"
        }
    }
private fun calculateWindDirection(windDirectionValue: Double?, optimalWindConditions: Map<String, Double>): String{
    val minWind = optimalWindConditions["min"] ?: 0.0
    val maxWind = optimalWindConditions["max"] ?: 360.0

    if (windDirectionValue != null) {
        return when {
            windDirectionValue < minWind -> "low"
            windDirectionValue > maxWind -> "high"
            else -> "correct"
        }
    }
    return "unknown"
}
