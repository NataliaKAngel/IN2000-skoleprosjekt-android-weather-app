package no.uio.ifi.in2000.natalan.havvarselapp.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.*
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.*

@Composable
fun InfoScreen(
    navController: NavController,
    infoScreenViewModel: InfoScreenViewModel
    ) {

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(
            Modifier
                .fillMaxSize()
                .background(color = DefaultBlue)
                .padding(16.dp)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = White, shape = RoundedCornerShape(size = 16.dp))
                    .padding(12.dp)
            ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Box(
                        modifier = Modifier
                            .padding(12.dp,12.dp,0.dp,0.dp)
                    ) {
                        GoToMap(navController)
                    }

                    Box {
                        Modifier
                            .width(328.dp)
                            .height(413.dp)
                            .background(color = White, shape = RoundedCornerShape(size = 16.dp))
                            .padding(0.dp,12.dp)
                        KiteConditionInfoBox()
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                }
            }
            // NavBar
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                NavBar(navController)
            }
        }
    }
}




