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
    suspend fun getRiskMatrixColor(areaName : String): String?{

        //The variable holds a MetAlertDataClass that contains a list of Feature
        val metAlertDataClass : MetAlertDataClass? = metAlertDataSource.getHavvarselData()

        // checking if metAlertDataClass is null or areaName is blank
        if(metAlertDataClass == null || areaName.isBlank()){
            return null
        }

        // finding the feature corresponding to the specific area
        val feature = metAlertDataClass.features.find{it.properties.area == areaName}

        // if the area is not found, return null
        if(feature == null){
            return null
        }
        // returning the risk matrix color for the specific area
        return feature.properties.riskMatrixColor
    }

    //TODO: Write method
    // The method fetches the awarness seriousness for a specific area
    suspend fun getAwarenessSeriousness(areaName : String): String?{

        //The variable holds a MetAlertDataClass that contains a list of Feature
        val metAlertDataClass : MetAlertDataClass? = metAlertDataSource.getHavvarselData()

        // checking if metAlertDataClass is null or areaName is blank
        if(metAlertDataClass == null || areaName.isBlank()){
            return null
        }
        // finding the feature corresponding to the specific area
        val feature = metAlertDataClass.features.find{it.properties.area == areaName}

        // if the area is not found, return null
        if(feature == null){
            return null
        }
        // returning the awarness seriousness for the specific area
        return feature.properties.awarenessSeriousness
    }

    // a method that fetches the name of a specific area from MetAlertDataClass
    //USIKKER PÅ OM DET ER SÅNN VI VIL HENTE NAVNET TIL AREA PÅ!!
    suspend fun getAreaName(areaIndex : Int): String?{
        // This variable holds a MetAlertDataClass that contains a list of Feature
        val metAlertDataClass : MetAlertDataClass? = metAlertDataSource.getHavvarselData()

        // if metAlertDataClass i null or the areaIndex is out of bounds, return null
        if(metAlertDataClass == null || areaIndex < 0 || areaIndex > metAlertDataClass.features.size){
            return null
        }

        // getting the feature based on a specific index

        val feature = metAlertDataClass.features[areaIndex]

        // returning the area name from the specific feature
        return feature.properties.area
    }



}