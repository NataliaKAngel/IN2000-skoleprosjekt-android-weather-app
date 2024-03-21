package no.uio.ifi.in2000.natalan.havvarselapp.ui.metAlerts


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts.MetAlertRepository
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.Properties

data class UIStateMetAlert(
    val mADataMap: Map<String, List<Properties>> =  emptyMap()
)
class MetAlertViewModel : ViewModel() {
    private val metAlertRepository = MetAlertRepository()

    private val _UIStateMetAlert = MutableStateFlow(UIStateMetAlert())
    val metAlertUIState: StateFlow<UIStateMetAlert> = _UIStateMetAlert.asStateFlow()

    init {
        fetchMetAlerts()
    }

    private fun fetchMetAlerts() {
        viewModelScope.launch {
            val alertMap = metAlertRepository.getAlertMap()
            // Filter only the first two areas
            val filteredMap = alertMap.entries.take(2).associate { it.key to it.value }
            _UIStateMetAlert.value = UIStateMetAlert(filteredMap)
        }
    }

    /*private fun fetchMetAlerts() {
        viewModelScope.launch {
            val alertMap = metAlertRepository.getAlertMap()
            _UIStateMetAlert.value = UIStateMetAlert(alertMap)
        }
    }

    suspend fun fetchCoordinates(): List<List<List<Any?>>>? {
        return metAlertRepository.getCoordinates()
    }

    suspend fun getAlertsForArea(area: String): List<Properties>? {
        return metAlertRepository.getAlertsForArea(area)
    }*/
}
