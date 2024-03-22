package no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts

import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertDataClass
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.Properties

class MetAlertRepository(
    private val metAlertDataSource: MetAlertDataSource
) {
    suspend fun getAlertMap(): Map<String, List<Properties>> {
        // Holds a MetAlertDataClass that contains a list of Feature
        val metAlertDataClass: MetAlertDataClass? = metAlertDataSource.getHavvarselData()

        // alertMap = MutableMap<String, MutableList<Properties>>: String = area, MutableList<Properties> = List<MetAlertDataSource.feature.properties>
        val alertMap: MutableMap<String, MutableList<Properties>> = mutableMapOf()
        metAlertDataClass?.features?.forEach { feature ->
            val area = feature.properties.area
            if (area.isNotBlank()) {
                alertMap.getOrPut(area) { mutableListOf() }.add(feature.properties)
            }
        }
        return alertMap
    }

    //TODO: Write comments to explain this method
    suspend fun getAlertsForArea(area: String): List<Properties>? {
        val alertMap = getAlertMap()
        return alertMap[area]
    }

    //TODO: Write comments to explain this method
    suspend fun getCoordinates(): List<List<List<Any?>>>? {
        val metAlertDataClass: MetAlertDataClass? = metAlertDataSource.getHavvarselData()
        return metAlertDataClass?.features?.map { feature ->
            feature.geometry.coordinates
        }
    }

    //TODO: Write method
    fun getRiskMatrixColor(): String{
        return ""
    }

    //TODO: Write method
    fun getAwarenessSeriousness(): String{
        return ""
    }

    //TODO: Write method
    fun getAreaName(): String{
        return ""
    }
}