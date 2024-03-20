package no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForcast.WeatherResponse

class HomeScreen {
    @SuppressLint("NotConstructor")
    @Composable
    fun HomeScreen(
        navController: NavController,
        locationForestViewModel: LocationForestViewModel
    ) {
        val UIStateLocation by locationForestViewModel.locationUIState.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Havvarsel",
                modifier = Modifier.padding(8.dp),
                style = TextStyle(fontSize = 35.sp)
            )
            UIStateLocation.lfDataMap.forEach { (coordinates, weatherResponse) ->
                CoordinateBox(coordinates = coordinates, weatherResponse = weatherResponse)
            }
        }
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Row {
                Button(
                    onClick = { navController.navigate("WarningScreen")},
                    modifier = Modifier
                        .weight(1f),
                    shape = MaterialTheme.shapes.medium.copy(CornerSize(0.dp))
                ) {
                    Text("til neste skjerm")
                }
            }


        }
    }

    @Composable
    fun CoordinateBox(coordinates: String, weatherResponse: WeatherResponse?, modifier: Modifier = Modifier) {
        Row(modifier = Modifier.padding(2.dp), verticalAlignment = Alignment.CenterVertically) {
            if (weatherResponse != null && weatherResponse.properties?.meta != null) {
                val meta = weatherResponse.properties.meta
                val units = weatherResponse.properties.meta.units //Henter hvilken form det står på. (glemte begrepet for dette)
                val windSpeed = weatherResponse.properties.timeseries.firstOrNull()?.data?.instant?.details?.getOrDefault("wind_speed", "0.0") // Henter ut Vind meter per sekund
                val airTemperatur = weatherResponse.properties.timeseries.firstOrNull()?.data?.instant?.details?.getOrDefault("air_temperature", "0.0") // Henter ut luft temperatur
                val airPressure = weatherResponse.properties.timeseries.firstOrNull()?.data?.instant?.details?.getOrDefault("air_pressure_at_sea_level", "0.0") // Henter ut luft trykk

                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)

                ) {
                    Text(text = "Coordinates: $coordinates")
                    val hPa = meta.units["air_pressure_at_sea_level"]
                    Text(text = "Air Pressure: $airPressure ${hPa ?: "N/A"}")

                    Text(text = "Air Temperature: $airTemperatur ℃")

                    val m_s = units["wind_speed"]
                    Text(text = "Wind Speed: $windSpeed ${m_s ?: "N/A"}")
                }
        } else {
            // Handle case where weatherResponse or its properties are null
            Text(text = "No data available for $coordinates")
            }
        }
    }
}
