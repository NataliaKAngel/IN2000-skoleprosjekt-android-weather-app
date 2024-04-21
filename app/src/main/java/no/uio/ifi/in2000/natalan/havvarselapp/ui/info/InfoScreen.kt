package no.uio.ifi.in2000.natalan.havvarselapp.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.*
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.*




@Composable
fun InfoScreen(navController: NavController) {

    // Define constraints
    val constraints = ConstraintSet {
        // Create refs to use
        val whiteBox = createRefFor("whiteBox")
        val goToMap = createRefFor("goToMap")
        val kiteConditionInfoBox = createRefFor("kiteConditionInfoBox")
        val infoColorsColumn = createRefFor("infoColorsColumn")
        val navBar = createRefFor("navBar")

        // Set constraints for whiteBox to fill the screen
        constrain(whiteBox) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        // Set constraints for goToMap
        constrain(goToMap) {
            top.linkTo(whiteBox.top)
            start.linkTo(parent.start)
        }

        // Set constraints for kiteConditionInfoBox
        constrain(kiteConditionInfoBox) {
            top.linkTo(goToMap.bottom)
        }

        // Set constraints for infoColorsColumn
        constrain(infoColorsColumn) {
            top.linkTo(kiteConditionInfoBox.bottom)
            bottom.linkTo(navBar.top) // Stop over navBar
            height = Dimension.value(360.dp)
        }

        // Set constraints for navBar to be at the bottom of the whiteBox
        constrain(navBar) {
            bottom.linkTo(whiteBox.bottom)
            start.linkTo(infoColorsColumn.start)
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
                .padding(start = 20.dp, top = 16.dp, end = 16.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .layoutId("goToMap")

                ) {
                    GoToMap(navController)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Box(
                    modifier = Modifier
                        .layoutId("kiteConditionInfoBox")
                ) {
                    KiteConditionInfoBox()
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .layoutId("infoColorsColumn")
                ) {
                    InfoColorsColumn()
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        
        Box(
            modifier = Modifier.layoutId("navBar")
        ) {
            NavBar(navController = navController)
        }
    }
}

