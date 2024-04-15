package no.uio.ifi.in2000.natalan.havvarselapp.ui.spot

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
fun SpotScreen (
    navController: NavController,
    spotScreenViewModel: SpotScreenViewModel
) {
    //Collecting the state flow from spotScreenViewModel
    val _spotUIState by spotScreenViewModel.spotUIState.collectAsState()

    //Getting the map from the UI-state (Map<PredefinedSpots, Spot?>)
    val spotMap = _spotUIState.spots

    //Getting the spots objects from the map (Collection<Spot?>)
    val spots = spotMap.values

    //Test: Display info in the Spot-objects
    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        items(spots.toList()){spot ->
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
            Text("Koordinater: ${spot?.coordinates}")
            Spacer(modifier = Modifier.height(5.dp))
            Text("Spotnavn: ${spot?.spotName}")
            Spacer(modifier = Modifier.height(5.dp))
            Text("By: ${spot?.cityName}")
            Spacer(modifier = Modifier.height(5.dp))
            Text("Vindstyrke: ${spot?.windSpeed}")
            Spacer(modifier = Modifier.height(5.dp))
            Text("Vindretning: ${spot?.windDirection}")
            Spacer(modifier = Modifier.height(5.dp))
            Text("Enheter: ${spot?.units}")
            Spacer(modifier = Modifier.height(5.dp))
            Text("Beregning: ${spot?.recommendationColor}")
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}