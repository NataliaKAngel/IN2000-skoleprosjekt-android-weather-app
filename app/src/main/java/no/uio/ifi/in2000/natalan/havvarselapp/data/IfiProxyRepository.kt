package no.uio.ifi.in2000.natalan.havvarselapp.data

import io.ktor.client.statement.HttpResponse

class IfiProxyRepository(private val dataSource: IfiProxyDataSource) {

    suspend fun getIfiProxy(): HttpResponse {
        return dataSource.fetchIfiProxy()
    }

}