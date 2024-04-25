package no.uio.ifi.in2000.natalan.havvarselapp

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}