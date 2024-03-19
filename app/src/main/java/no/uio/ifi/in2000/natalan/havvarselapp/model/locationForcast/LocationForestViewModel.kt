package no.uio.ifi.in2000.natalan.havvarselapp.model.locationForcast

import android.util.Log
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
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForcast.LocationForecastDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForcast.LocationForecastRepository
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.MetAlertsViewModel
class LocationForestViewModel : ViewModel() {
    // Example LiveData for Weather and Wind
    private val _weatherAndWindLiveData = MutableLiveData<String>()
    val weatherAndWindLiveData: LiveData<String> = _weatherAndWindLiveData
    // Example function to fetch Weather and Wind data
    suspend fun fetchWeatherAndWindData(){
// Fetch data here and update the LiveData
        _weatherAndWindLiveData.value = "Weather and Wind data fetched successfully"

        /*val lfr = LocationForecastRepository();



        println(weatherRespons)*/

        /*val lfr = LocationForecastRepository()
        val weatherRespons = lfr.getLocationForecast("9.58", "60.1", "496");
        println(weatherRespons?.type)
        Log.d("Liste", "$weatherRespons?.type")

        val details = weatherRespons?.properties?.timeseries?.get(0)?.data?.instant?.details
        if (details != null) {
            println("Details: $details")
            Log.d("Details", "$details")
        }*/
    }
}
@Composable
fun LocationForestScreen(
    navController: NavController = rememberNavController()
) {
    Column {
        Text("Her kommer Weather and Wind API informasjon")
        Button(
            onClick = {
                navController.navigate("metAlerts")
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Knapp til forrige skjerm")
        }
    }
}