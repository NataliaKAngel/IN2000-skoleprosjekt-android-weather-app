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
class MetAlertViewModel (
    private val metAlertRepository: MetAlertRepository
): ViewModel() {

    //UI State: Map<String, List<Properties>>
    private val _UIStateMetAlert = MutableStateFlow(UIStateMetAlert())
    val metAlertUIState: StateFlow<UIStateMetAlert> = _UIStateMetAlert.asStateFlow()

    //TODO: Make UI-states that provides areaName, awerenessSeriousness and riskMatrixColor



    //TODO: Remove this init block when repository and viewModel is up to date
    init {
        fetchMetAlerts()
    }

    //TODO: Remove this function when repository and viewModel is up to date
    private fun fetchMetAlerts() {
        viewModelScope.launch {
            val alertMap = metAlertRepository.getAlertMap()
            // Filter only the first two areas
            val filteredMap = alertMap.entries.take(2).associate { it.key to it.value }
            _UIStateMetAlert.value = UIStateMetAlert(filteredMap)
        }
    }
}
