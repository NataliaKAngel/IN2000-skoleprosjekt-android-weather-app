package no.uio.ifi.in2000.natalan.havvarselapp.data

import io.ktor.client.statement.HttpResponse

class IfiProxyRepository(private val dataSource: IfiProxyDataSource) {

    suspend fun getIfiProxy(): HttpResponse {
        return dataSource.fetchIfiProxy()
    }

    suspend fun getProjectionVariables(): List<String> {
        return dataSource.fetchProjectionVariables()
    }

    suspend fun getAllVariables(): List<String> {
        return dataSource.fetchAllVariables()
    }

    suspend fun getDepths(): List<Double> {
        return dataSource.fetchDepths()
    }

    suspend fun getTimes(): List<String> {
        return dataSource.fetchTimes()
    }

    suspend fun getTemperatureProjection(lon: Double, lat: Double): Map<String, Double> {
        return dataSource.fetchTemperatureProjection(lon, lat)
    }

    suspend fun getWindAndCurrentProjection(lon1: Double, lat1: Double, lon2: Double, lat2: Double, time: String): Map<String, Double> {
        return dataSource.fetchWindAndCurrentProjection(lon1, lat1, lon2, lat2, time)
    }

}