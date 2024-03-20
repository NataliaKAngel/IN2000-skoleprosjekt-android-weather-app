package no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts

import no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts.Properties


class MetAlertRepository {
    private val dataSource = MetAlertDataSource()

    suspend fun getAlertMap(): Map<String, List<Properties>> {
        val metAlertDataClass: MetAlertDataClass? = dataSource.getHavvarselData()
        val alertMap: MutableMap<String, MutableList<Properties>> = mutableMapOf()
        metAlertDataClass?.features?.forEach { feature ->
            val area = feature.properties.area
            if (area.isNotBlank()) {
                alertMap.getOrPut(area) { mutableListOf() }.add(feature.properties)
            }
        }
        return alertMap
    }

    suspend fun getAlertsForArea(area: String): List<Properties>? {
        val alertMap = getAlertMap()
        return alertMap[area]
    }

    suspend fun getCoordinates(): List<List<List<Any?>>>? {
        val metAlertDataClass: MetAlertDataClass? = dataSource.getHavvarselData()
        return metAlertDataClass?.features?.map { feature ->
            feature.geometry.coordinates
        }
    }
    /*

    fun isCoordinateInsidePolygon(latitude: Double, longitude: Double, polygon: List<List<List<Double>>>): Boolean {
        val x = longitude
        val y = latitude
        var isInside = false

        polygon.forEach { subPolygon ->
            var j = subPolygon.size - 1
            for (i in subPolygon.indices) {
                val xi = subPolygon[i][0]
                val yi = subPolygon[i][1]
                val xj = subPolygon[j][0]
                val yj = subPolygon[j][1]
                val intersect = (yi > y) != (yj > y) && x < (xj - xi) * (y - yi) / (yj - yi) + xi
                if (intersect) isInside = !isInside
                j = i
            }
        }

        return isInside

    }

    suspend fun getAlertsForCoordinate(latitude: Double, longitude: Double): List<Properties> {
        val dataSource = HavvarselDataSource()
        val havvarselData = dataSource.getHavvarselData()
        val alerts: MutableList<Properties> = mutableListOf()

        if (havvarselData != null) {
            // Iterate through each feature to check if the coordinate falls within its geometry
            havvarselData.features.forEach { feature ->
                val geometry = feature.geometry
                println("Geometry type: ${geometry.type}")
                if (isCoordinateInsideGeometry(latitude, longitude, geometry)) {
                    alerts.add(feature.properties)
                }
            }
        } else {
            println("Failed to retrieve Havvarsel data.")
        }

        return alerts
    }

    private suspend fun isCoordinateInsideGeometry(
        latitude: Double,
        longitude: Double,
        geometry: Geometry
    ): Boolean {
        return when (geometry.type) {
            "Polygon" -> isCoordinateInsidePolygon(latitude, longitude, convertCoordinates(geometry.coordinates.first()))
            "MultiPolygon" -> geometry.coordinates.any { polygon ->
                isCoordinateInsidePolygon(latitude, longitude, convertCoordinates(polygon.first()))
            }
            else -> false
        }
    }

    private fun convertCoordinates(coordinates: List<List<Any?>>): List<List<List<Double>>> {
        return coordinates.map { polygon ->
            polygon.map { point ->
                (point as List<Any?>).map { it.toString().toDouble() }
            }
        }
    }

     */

}
/*

suspend fun testGetAlertFromCoordinate() {
    val rep = HavvarselRepository()
    // Define some test coordinates
    val testCoordinates = listOf(
        Pair(5.1937, 59.3467 ),  // Coordinate inside a polygon
        Pair(5.1982, 59.581),  // Coordinate inside a polygon
        Pair(5.1683, 59.6838),  // Coordinate inside a polygon
        Pair(5.1293, 59.7672),  // Coordinate outside all polygons
    )

    // Call getAlertFromCoordinate for each test coordinate
    testCoordinates.forEach { (latitude, longitude) ->
        println("Testing coordinate: ($latitude, $longitude)")
        val alerts = rep.getAlertsForCoordinate(latitude, longitude)
        if (alerts.isNotEmpty()) {
            println("Alerts:")
            alerts.forEach { alert ->
                println("Altitude Above Sea Level: ${alert.altitudeAboveSeaLevel}")
                println("Awareness Response: ${alert.awarenessResponse}")
                println("Awareness Seriousness: ${alert.awarenessSeriousness}")
                println("Awareness Level: ${alert.awarenessLevel}")
                println("Awareness Type: ${alert.awarenessType}")
                println("Ceiling above sea level: ${alert.ceilingAboveSeaLevel}")
                println("Certainty: ${alert.certainty}")
                println("Consequences: ${alert.consequences}")
                println("Description: ${alert.description}")
                println("Event: ${alert.event}")
                println("Event Awareness Name: ${alert.eventAwarenessName}")
                println("Event Ending Time: ${alert.eventEndingTime}")
                println("Geographic domain: ${alert.geographicDomain}")
                println("Instruction: ${alert.instruction}")
                println("Resources: ${alert.resources}")
                println("Risk Matrix Color: ${alert.riskMatrixColor}")
                println("Severity: ${alert.severity}")
                println("Status: ${alert.status}")
                println("Title: ${alert.title}")
                println("Trigger Level: ${alert.triggerLevel}")
                println("Type: ${alert.type}")
                println("Municipality: ${alert.municipality}")
            }
        } else {
            println("No alerts found for this coordinate.")
        }
        println("--------------------")
    }
}
suspend fun main(){
    testGetAlertFromCoordinate()
}


 */
/*suspend fun testHavRept(rep: HavvarselRepository){
    val map = rep.getAlertMap()
    val i = map.keys.iterator()
    while(i.hasNext()){
        val area = i.next()
        val alerts = map[area]
        println("Area: ${area}\n Alerts: $alerts")
    }
}
suspend fun testHavRep(rep: HavvarselRepository) {
    val map = rep.getAlertMap()
    for ((area, alerts) in map) {
        println("Area: $area")
        println("Alerts:")
        for (alert in alerts) {
            println("Altitude Above Sea Level: ${alert.altitudeAboveSeaLevel}")
            println("Awareness Response: ${alert.awarenessResponse}")
            println("Awareness Seriousness: ${alert.awarenessSeriousness}")
            println("Awareness Level: ${alert.awarenessLevel}")
            println("Awareness Type: ${alert.awarenessType}")
            println("Ceiling above sea level: ${alert.ceilingAboveSeaLevel}")
            println("Certainty: ${alert.certainty}")
            println("Consequences: ${alert.consequences}")
            println("Description: ${alert.description}")
            println("Event: ${alert.event}")
            println("Event Awareness Name: ${alert.eventAwarenessName}")
            println("Event Ending Time: ${alert.eventEndingTime}")
            println("Geographic domain: ${alert.geographicDomain}")
            //println("Id: ${alert.properties.id}")
            println("Instruction: ${alert.instruction}")
            println("Resources: ${alert.resources}")
            println("Risk Matrix Color: ${alert.riskMatrixColor}")
            println("Severity: ${alert.severity}")
            println("Status: ${alert.status}")
            println("Title: ${alert.title}")
            println("Trigger Level: ${alert.triggerLevel}")
            println("Type: ${alert.type}")
            println("Municipality: ${alert.municipality}")


        }
    }
}

suspend fun testHavvarselRep(rep: HavvarselRepository, area: String){


    // Henter Havvarsel data
    val alertMap = rep.getAlertMap()

    //Henter varsler for hvert område
    val alertsForArea = rep.getAlertsForArea(area)

    // Skriver ut advarslene for hvert område
    if(alertsForArea != null){
        println("Alerts for area '$area':")
        alertsForArea.forEach{ alert ->
            println("Altitude Above Sea Level: ${alert.altitudeAboveSeaLevel}")
            println("Awareness Response: ${alert.awarenessResponse}")
            println("Awareness Seriousness: ${alert.awarenessSeriousness}")
            println("Awareness Level: ${alert.awarenessLevel}")
            println("Awareness Type: ${alert.awarenessType}")
            println("Ceiling above sea level: ${alert.ceilingAboveSeaLevel}")
            println("Certainty: ${alert.certainty}")
            println("Consequences: ${alert.consequences}")
            println("Contact: ${alert.contact}")
            println("County: ${alert.county}")
            println("Description: ${alert.description}")
            println("Event: ${alert.event}")
            println("Event Awareness Name: ${alert.eventAwarenessName}")
            println("Event Ending Time: ${alert.eventEndingTime}")
            println("Geographic domain: ${alert.geographicDomain}")
            println("Id: ${alert.id}")
            println("Instruction: ${alert.instruction}")
            println("Resources: ${alert.resources}")
            println("Risk Matrix Color: ${alert.riskMatrixColor}")
            println("Severity: ${alert.severity}")
            println("Status: ${alert.status}")
            println("Title: ${alert.title}")
            println("Trigger Level: ${alert.triggerLevel}")
            println("Type: ${alert.type}")
            println("Web: ${alert.web}")
            println("Municipality: ${alert.municipality}")
            println("----")
        }
    }else{
        println("No alerts found for area '$area")
    }


}



suspend fun main() {
    val dataSource = HavvarselDataSource()
    val rep = HavvarselRepository()
    //val area = "Karmøy - Fedje" // Specify the area you want to test

    //testHavvarselRep(rep, area)
    testHavRep(rep)
}*/