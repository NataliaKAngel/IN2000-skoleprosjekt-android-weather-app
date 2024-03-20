package no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForcast.WeatherResponse
import androidx.compose.foundation.layout.*

enum class District {
    Hammersanden,
    Havika,
    Bore,
    Oslo
}
class HomeScreen {



    val _selectedOption = MutableLiveData(District.Hammersanden)
    val selectedOption: LiveData<District> = _selectedOption

    fun setSelectedOption(option: District) {
        _selectedOption.value = option
    }
    @SuppressLint("NotConstructor")
    @Composable
    fun HomeScreen(
        navController: NavController,
        locationForestViewModel: LocationForestViewModel
    ) {

        val UIStateLocation by locationForestViewModel.locationUIState.collectAsState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item {
                Text(
                    "Havvarsel",
                    modifier = Modifier.padding(8.dp),
                    style = TextStyle(fontSize = 35.sp)
                )
            }

            item {
                DropDownMenu(selectedOption)
                SpotCard(navController, locationForestViewModel, "Hammersanden", "58.186", "8.072")
            }
            item {
                SpotCard(navController, locationForestViewModel, "Havika", "60.0", "9.9")
            }
            item {
                SpotCard(navController, locationForestViewModel, "Bore", "70.0", "9.9")
            }
            item {
                SpotCard(navController, locationForestViewModel,"Oslo", "80.0", "9.9")
            }

            UIStateLocation.lfDataMap.forEach { (coordinates, weatherResponse) ->
                item {
                    CoordinateBox(coordinates = coordinates, weatherResponse = weatherResponse)
                }
            }
            item {
                Button(
                    onClick = {
                        navController.navigate("metAlerts")
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Knapp til neste skjerm")
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
                val airTemperature = weatherResponse.properties.timeseries.firstOrNull()?.data?.instant?.details?.getOrDefault("air_temperature", "0.0") // Henter ut luft temperatur
                val airPressure = weatherResponse.properties.timeseries.firstOrNull()?.data?.instant?.details?.getOrDefault("air_pressure_at_sea_level", "0.0") // Henter ut luft trykk
                val windDirection = weatherResponse.properties.timeseries.firstOrNull()?.data?.instant?.details?.get("wind_from_direction")
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    Text(text = "Coordinates: $coordinates")
                    val hPa = meta.units["air_pressure_at_sea_level"]
                    Text(text = "Air Pressure: $airPressure ${hPa ?: "N/A"}")

                    Text(text = "Air Temperature: $airTemperature ℃")

                    val m_s = units["wind_speed"]
                    Text(text = "Wind Speed: $windSpeed ${m_s ?: "N/A"}")

                    val v_d = units["wind_from_direction"]
                    Text(text = "Wind direction: $windDirection ${v_d ?: "N/A"}")
                }
            } else {
                // Handle case where weatherResponse or its properties are null
                Text(text = "No data available for $coordinates")
            }
        }
    }

    @Composable
    fun DropDownMenu(selectedOption: LiveData<District>) {
        var expanded by remember { mutableStateOf(false) }

        Column {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                District.values().forEach { district ->
                    DropdownMenuItem(
                        text = { Text(district.toString()) },
                        onClick = {
                            expanded = false
                            setSelectedOption(district)
                        }
                    )
                }
            }

            Button(
                onClick = { expanded = true },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("velg spot for å se info")
            }
        }
    }

    @Composable
    fun SpotCard(
        navController: NavController = rememberNavController(),
        locationForestViewModel: LocationForestViewModel,
        stedsnavn: String,
        lat: String,
        lon: String
    ) {
        Card(
            modifier = Modifier
                .clickable {
                    locationForestViewModel.changeCoordinates(lat, lon) // This line is adjusted to include the view model update
                }
                .fillMaxWidth()
                .padding(1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "SpotCard",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stedsnavn,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
