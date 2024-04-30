package no.uio.ifi.in2000.natalan.havvarselapp.UnitTest

import org.junit.Assert
import org.junit.Test

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

    fun createAllAlertInfos(features: List<Feature>?): List<AlertInfo> {
        return features?.map { createAlertInfo(it) } ?: emptyList()
    }

    fun createAlertInfo(feature: Feature): AlertInfo {
        return AlertInfo(
            riskMatrixColor = feature.properties.riskMatrixColor,
            description = feature.properties.description,
            event = feature.properties.event,
            startTime = feature.whenField.interval[0],
            endTime = feature.whenField.interval[1]
        )
    }
}