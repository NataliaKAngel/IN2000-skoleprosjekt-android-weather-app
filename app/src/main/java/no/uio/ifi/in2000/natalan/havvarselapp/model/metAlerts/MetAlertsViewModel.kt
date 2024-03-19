package no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts;
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
class MetAlertsViewModel : ViewModel() {
    // You can have LiveData or State variables here to hold data for your UI
// Example LiveData
    private val _metAlertsLiveData = MutableLiveData<String>()
    val metAlertsLiveData: LiveData<String> = _metAlertsLiveData
    // Example function to fetch MetAlerts data
    fun fetchMetAlertsData() {
// Fetch data here and update the LiveData
        _metAlertsLiveData.value = "MetAlerts data fetched successfully"
    }
}
@Composable
fun MetAlertsScreen(
    navController: NavController = rememberNavController()
) {
    Column {
        Text("Her kommer MetAlerts API informasjon")
        Button(
            onClick = {
                navController.navigate("locationForcast")
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Knapp til neste skjerm")
        }
    }
}