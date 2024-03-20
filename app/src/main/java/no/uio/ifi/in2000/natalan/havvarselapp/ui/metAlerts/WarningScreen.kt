package no.uio.ifi.in2000.natalan.havvarselapp.ui.metAlerts

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts.Feature
import no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts.Properties

class WarningScreen {
    @SuppressLint("NotConstructor")
    @Composable
    fun WarningScreen(
        navController: NavController,
        metAlertViewModel: MetAlertViewModel
    ) {
        val metAlertUIState by metAlertViewModel.metAlertUIState.collectAsState()
        val coordinates = remember { mutableStateListOf<List<List<List<Any?>>>>() }
        val selectedAreaProperties = remember { mutableStateOf<List<Properties>?>(null) }
        val selectedAreaIndex = remember { mutableStateOf(0) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Met Alerts",
                modifier = Modifier.padding(8.dp),
                style = TextStyle(fontSize = 35.sp)
            )
            Text("Havvarsel-Met Alerts", fontSize = 20.sp)
            Button(onClick = {
                selectedAreaIndex.value = 0
                selectedAreaProperties.value = metAlertUIState.mADataMap.values.elementAtOrNull(selectedAreaIndex.value)
            }) {
                Text("Fedje-Måløy")
            }
            Button(onClick = {
                selectedAreaIndex.value = 1
                selectedAreaProperties.value = metAlertUIState.mADataMap.values.elementAtOrNull(selectedAreaIndex.value)
            }) {
                Text("Måløy-Svinøy")
            }
            CoordinateBox(
                //features = metAlertUIState.mADataMap.values.elementAtOrNull(selectedAreaIndex.value) ?: emptyList(),
                properties = selectedAreaProperties.value,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                Row {
                    Button(
                        onClick = { navController.navigate("HomeScreen")},
                        modifier = Modifier
                            .weight(1f),
                        shape = MaterialTheme.shapes.medium.copy(CornerSize(0.dp))
                    ) {
                        Text("til neste skjerm")
                    }
                }


            }
        }
    }


    @Composable
    fun CoordinateBox(
        //features: List<Feature>,
        properties: List<Properties>?,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier.padding(8.dp)
        ) {
            if (properties != null){
                properties.forEachIndexed { index, properties ->
                    // Only display properties for the first two features
                    if (index < 2) {
                        Text(
                            text = "Area ${index + 1}:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Risk Matrix Color: ${properties.riskMatrixColor}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "Awareness Seriousness: ${properties.awarenessSeriousness}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
            } else {
                Text(
                    text = "No features available",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}