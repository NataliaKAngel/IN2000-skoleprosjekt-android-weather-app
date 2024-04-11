package no.uio.ifi.in2000.natalan.havvarselapp.ui.metAlerts


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UIStateMetAlert(
    val areaName: String? = "",
    val awerenessSeriousness:String? = "",
    val riskMatrixColor: String? = ""
)
class MetAlertViewModel (
   // private val metAlertRepository: MetAlertRepository
): ViewModel() {

    private val _UIStateMetAlert = MutableStateFlow(UIStateMetAlert())
    val metAlertUIState: StateFlow<UIStateMetAlert> = _UIStateMetAlert.asStateFlow()

    //TODO: Make UI-states that provides areaName, awerenessSeriousness and riskMatrixColor
    private fun fetchMetAlerts(index: Int){
        viewModelScope.launch {
//            val areaName = metAlertRepository.getAreaName(index)
//            val awerenessSeriousness = areaName?.let { metAlertRepository.getAwarenessSeriousness(it) }
//            val riskMatrixColor = areaName?.let { metAlertRepository.getRiskMatrixColor(it) }

            // Update UIStateLocation with fetched data
            _UIStateMetAlert.value = UIStateMetAlert(
//                areaName,
//                awerenessSeriousness,
//                riskMatrixColor
            )
        }
    }

//    // en test funksjon som bare skriver ut mange stedsnavn på index, men det er noe rart jeg ikke skjønner her
//    private suspend fun fetchMetAlertsAreaNames(){
//        val areaNameList = mutableListOf<String>()
////        for (i in 0 until 100) {
////            val areaName = metAlertRepository.getAreaName(i)
////            if (areaName != null) {
////                Log.i("check", "Index: $i, AreaName: $areaName")
////                areaNameList.add(areaName)
////            } else {
////                Log.i("check", "Index: $i, AreaName is null")
//           }
//        }
//    }
//
//    fun fetchMetAlertsByIndex(index: Int) {
////        viewModelScope.launch {
////            try {
////                fetchMetAlerts(index)
////                fetchMetAlertsAreaNames()
////            } catch (e: Exception) {
////                // skriv catch kommentar
////            }
////        }
//    }

}
