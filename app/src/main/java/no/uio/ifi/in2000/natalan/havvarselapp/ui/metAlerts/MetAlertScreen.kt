package no.uio.ifi.in2000.natalan.havvarselapp.ui.metAlerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts.Properties
import no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast.CoordinateBox

@Composable
fun MetAlertScreen(
    navController: NavController,
    metAlertViewModel: MetAlertViewModel
) {
    //UI-state flow:
    val metAlertUIState by metAlertViewModel.metAlertUIState.collectAsState()
    // Tester viewmodel og repository
    metAlertViewModel.fetchMetAlertsByIndex(1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "MetAlert",
            modifier = Modifier.padding(8.dp),
            style = TextStyle(fontSize = 35.sp)
        )
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                metAlertCard(
                    areaName = metAlertUIState.areaName,
                    awerenessSeriousness = metAlertUIState.awerenessSeriousness,
                    riskMatrixColor = metAlertUIState.riskMatrixColor
                )
            }
        }
    }
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

//TODO: Switch from using instance variables to show farge and awerenessSeriousness to UI-state flow
@Composable
fun metAlertCard(
    areaName: String?,
    awerenessSeriousness:String?,
    riskMatrixColor: String?
){
    // Display wind speed information
    Text("areaName: $areaName")
    Spacer(modifier = Modifier.height(8.dp))

    //
    Text("awerenessSeriousness: $awerenessSeriousness")
    Spacer(modifier = Modifier.height(8.dp))

    //
    Text("riskMatrixColor: $riskMatrixColor")
    Spacer(modifier = Modifier.height(8.dp))
}


