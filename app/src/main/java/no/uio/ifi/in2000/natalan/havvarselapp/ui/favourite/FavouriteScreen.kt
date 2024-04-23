package no.uio.ifi.in2000.natalan.havvarselapp.ui.favourite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.FavouriteScreenText
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.NavBar
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.SettingsScreenText
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.DefaultBlue
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.White

@Composable
fun FavouriteScreen (
    navController: NavController,
    favouriteScreenViewModel: FavouriteScreenViewModel
) {

// Define constraints
    val constraints = ConstraintSet {
        // Create refs to use
        val whiteBox = createRefFor("whiteBox")
        val favouriteScreenText = createRefFor("favouriteScreenText")
        val navBar = createRefFor("navBar")

        // Set constraints for whiteBox to fill the screen
        constrain(whiteBox) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        // Set constraints for settingsScreenText
        constrain(favouriteScreenText) {
            top.linkTo(parent.top)
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
                .padding(start = 28.dp, top = 72.dp, end = 28.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .layoutId("favouriteScreenText")
                ) {
                    FavouriteScreenText()
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
