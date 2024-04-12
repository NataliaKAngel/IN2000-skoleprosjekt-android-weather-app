package no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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

@Composable
fun LocationForecastScreen(
    navController: NavController,
    locationForecastViewModel: LocationForecastViewModel
) {
    // UI-state flow:
    val UIStateLocation by locationForecastViewModel.locationUIState.collectAsState()

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

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                UIStateLocation.windSpeedMap.keys.forEach { time ->
                    val windSpeed = UIStateLocation.windSpeedMap[time] ?: 0.0
                    CoordinateBox(
                        modifier = Modifier.padding(8.dp),
                        time = time,
                        windSpeed = windSpeed,
                        airTemperature = UIStateLocation.airTemperatureMap[time] ?: 0.0,
                        airPressure = UIStateLocation.airPressureMap[time] ?: 0.0,
                        windDirection = UIStateLocation.windDirectionMap[time] ?: 0.0,
                        units = UIStateLocation.units
                    )
                }
            }
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

//TODO: Move all weatherReponses to viewModel (UIStateFlow)

@Composable
fun CoordinateBox(
    modifier: Modifier = Modifier,
    time: String,
    windSpeed: Double,
    airTemperature: Double,
    airPressure: Double,
    windDirection: Double,
    units: Map<String?, String?>?
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("Time: $time")
        Spacer(modifier = Modifier.height(8.dp))

        // Display wind speed information
        Text("Wind Speed: $windSpeed ${units?.get("wind_speed")}") // Using units for wind speed
        Spacer(modifier = Modifier.height(8.dp))

        // Display air temperature information
        Text("Air Temperature: $airTemperature ${units?.get("air_temperature")}") // Using units for air temperature
        Spacer(modifier = Modifier.height(8.dp))

        // Display air pressure information
        Text("Air Pressure: $airPressure ${units?.get("air_pressure_at_sea_level")}") // Using units for air pressure
        Spacer(modifier = Modifier.height(8.dp))

        // Display wind direction information
        Text("Wind Direction: $windDirection ${units?.get("wind_from_direction")}") // Using units for wind direction
    }
}