package no.uio.ifi.in2000.natalan.havvarselapp.ui.spot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import no.uio.ifi.in2000.natalan.havvarselapp.model.predefinedSpots.PredefinedSpots
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

}