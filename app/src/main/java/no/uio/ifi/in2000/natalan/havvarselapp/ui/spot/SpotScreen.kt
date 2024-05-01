package no.uio.ifi.in2000.natalan.havvarselapp.ui.spot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import no.uio.ifi.in2000.natalan.havvarselapp.model.spot.SpotInfo
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.ButtonRow
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.DaysBoxRow
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.NavBar
//import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.NextDaysRows
//import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.NextHoursRow
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.SpotBoxForSpotScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.DefaultBlue
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.White

@Composable
fun SpotScreen (
    navController: NavController,
    spotScreenViewModel: SpotScreenViewModel
) {
    //Collecting the state flow from spotScreenViewModel
    val spotUIState by spotScreenViewModel.spotUIState.collectAsState()

    //Getting the spot from the UI-state (type: Spot?)
    val spot = spotUIState.spot


// Define constraints
        val constraints = ConstraintSet {
            // Create refs to use
            val whiteBox = createRefFor("whiteBox")
            val buttonRow = createRefFor("buttonRow")
            val spotBoxForSpotScreen = createRefFor("spotBoxForSpotScreen")
            val daysBoxRow = createRefFor("daysBoxRow")
            val nextHoursRow = createRefFor("nextHoursRow")
            val navBar = createRefFor("navBar")


            // Set constraints for whiteBox to fill the screen
            constrain(whiteBox) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }

            constrain(buttonRow) {
                top.linkTo(parent.top)
            }

            constrain(spotBoxForSpotScreen) {
                top.linkTo(buttonRow.top)
            }

            constrain(nextHoursRow) {
                top.linkTo(spotBoxForSpotScreen.top)
            }

            constrain(daysBoxRow) {
                top.linkTo(nextHoursRow.top)
            }

            // Set constraints for navBar to be at the bottom of the whiteBox
            constrain(navBar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        }

// Apply constraints to ConstraintLayout
        ConstraintLayout(
            constraints,
            modifier = Modifier
                .fillMaxSize()
                .background(color = DefaultBlue)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .layoutId("whiteBox")
                    .fillMaxSize() // Fill the entire ConstraintLayout
                    .background(color = White, shape = RoundedCornerShape(size = 16.dp))
                    .padding(start = 28.dp, top = 28.dp, end = 28.dp)
            ) {
                LazyColumn {
                    //spotBoxForSpotScreen

                    item {
                        Box(
                            modifier = Modifier
                                .layoutId("buttonRow")
                                .background(White, shape = RoundedCornerShape(size = 16.dp))

                        ) {
                            ButtonRow(navController)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Box(
                            modifier = Modifier
                                .layoutId("spotBoxForSpotScreen")
                                .background(White, shape = RoundedCornerShape(size = 16.dp))

                        ) {
                            SpotBoxForSpotScreen()
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        val spotDetailsByDate: List<List<SpotInfo>> = spot?.spotDetails
                            ?.groupBy { it.date }
                            ?.values
                            ?.toList()
                            ?: emptyList()

                        spotDetailsByDate.forEach { detailsForDate ->
                            Box(
                                modifier = Modifier
                                    .layoutId("daysRowBox")
                                    .background(White, shape = RoundedCornerShape(size = 16.dp))
                            ) {
                                DaysBoxRow(detailsForDate)
                            }
                        }

                    }

                }
            }
            Box(
                modifier = Modifier
                    .layoutId("navBar")
            ) {
                NavBar(navController = navController)
            }
        }
}


