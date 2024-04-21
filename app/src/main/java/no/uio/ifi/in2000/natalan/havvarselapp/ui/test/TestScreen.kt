package no.uio.ifi.in2000.natalan.havvarselapp.ui.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.Spot

@Composable
fun TestScreen(
    testScreenViewModel: TestScreenViewModel
){
    //Collecting the state flow from spotScreenViewModel
    val spotsUIState by testScreenViewModel.spotsUIState.collectAsState()

    //Getting the map from the UI-state (Map<PredefinedSpots, Spot?>)
    val spotMap = spotsUIState.spots

    //Getting the spots objects from the map (List<Spot?>)
    val spots = spotMap.values.toList()

    //Test: Display info in the Spot-objects
    LazyColumn (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(spots){spot ->
            SpotCard(
                spot = spot
            )
        }
    }
}

@Composable
fun SpotCard(
    spot: Spot?
){
    Card{
        Column {
            //Coordinates
            Text("Koordinater: ${spot?.coordinates}")
            Spacer(modifier = Modifier.height(5.dp))
            //SpotName
            Text("Spotnavn: ${spot?.spotName}")
            Spacer(modifier = Modifier.height(5.dp))
            //CityName
            Text("By: ${spot?.cityName}")
            Spacer(modifier = Modifier.height(5.dp))
            //MetAlertDataClass
            Text("MetAlertDataClass: ${spot?.metAlert}")
            Spacer(modifier = Modifier.height(5.dp))
            //Feature
            Text("Feature: ${spot?.feature}")
            Spacer(modifier = Modifier.height(5.dp))
            //RiskMatrixColor - MetAlerts
            Text("Farevarselfarge: ${spot?.riskMatrixColor}")
            Spacer(modifier = Modifier.height(5.dp))
            //Description - MetAlerts
            Text("Beskrivelse: ${spot?.description}")
            Spacer(modifier = Modifier.height(5.dp))
            //TriggerLevel - MetAlerts
            Text("TriggerLevel: ${spot?.triggerLevel}")
            Spacer(modifier = Modifier.height(5.dp))
            //WindSpeed - LocationForecast
            Text("Vindstyrke: ${spot?.windSpeed}, ${spot?.windSpeedUnit}")
            Spacer(modifier = Modifier.height(5.dp))
            //WindDirection - LocationForecast
            Text("Vindretning: ${spot?.windDirection}, ${spot?.windDirectionUnit}")
            Spacer(modifier = Modifier.height(5.dp))
            Text("Beregning: ${spot?.recommendationColor}")
        }
    }
}