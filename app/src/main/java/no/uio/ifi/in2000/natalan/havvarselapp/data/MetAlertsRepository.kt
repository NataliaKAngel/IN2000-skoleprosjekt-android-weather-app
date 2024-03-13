package no.uio.ifi.in2000.natalan.havvarselapp.data

import io.ktor.client.statement.HttpResponse

class MetAlertsRepository(private val dataSource: MetAlertsDataSource) {

    suspend fun getMetAlerts(): HttpResponse {
        return dataSource.fetchMetAlerts()
    }

}