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

    //Getting the spots objects from the map (List<Spot?>)
    val spots = spotsUIState.spots

    //Test: Display info in the Spot-objects
    LazyColumn (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(spots){spot ->
            SpotCard(
                spot = spot
            )
            Spacer(modifier = Modifier.height(20.dp))
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
            Text("Koordinater: ${spot?.predefinedSpot?.coordinates}")
            Spacer(modifier = Modifier.height(5.dp))
            //SpotName
            Text("Spotnavn: ${spot?.predefinedSpot?.spotName}")
            Spacer(modifier = Modifier.height(5.dp))
            //CityName
            Text("By: ${spot?.predefinedSpot?.cityName}")
            Spacer(modifier = Modifier.height(5.dp))
            if(spot?.alerts?.isNotEmpty() == true){
                //RiskMatrixColor - MetAlerts
                Text("Farevarselfarge: ${spot.alerts[0]?.riskMatrixColor}")
                Spacer(modifier = Modifier.height(5.dp))
                //Event
                Text("Event: ${spot.alerts[0]?.event}")
                Spacer(modifier = Modifier.height(5.dp))
                //Description - MetAlerts
                Text("Beskrivelse: ${spot.alerts[0]?.description}")
                Spacer(modifier = Modifier.height(5.dp))
            }

            //WindSpeed - LocationForecast
            Text("Vindstyrke: ${spot?.spotDetails?.get(0)?.windSpeedValue}, ${spot?.spotDetails?.get(0)?.windSpeedUnit}")
            Spacer(modifier = Modifier.height(5.dp))
            //WindDirection - LocationForecast
            Text("Vindretning: ${spot?.spotDetails?.get(0)?.windDirectionString}, ${spot?.spotDetails?.get(0)?.windDirectionUnit}")
            Spacer(modifier = Modifier.height(5.dp))
            Text("Beregning: ${spot?.spotDetails?.get(0)?.kiteRecommendationColor}")
            Spacer(modifier = Modifier.height(5.dp))
            Text("Dato: ${spot?.spotDetails?.get(0)?.date}")

        }
    }
}