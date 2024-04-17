package no.uio.ifi.in2000.natalan.havvarselapp.ui.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.*
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.*

//@Composable
//fun InfoScreen(
//    navController: NavController
//    ) {
//
//    Column(
//        verticalArrangement = Arrangement.SpaceBetween,
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ) {
//
//        Box(
//            Modifier
//                .fillMaxSize()
//                .background(color = DefaultBlue)
//                .padding(16.dp)
//        ) {
//            Box(
//                Modifier
//                    .fillMaxSize()
//                    .background(color = White, shape = RoundedCornerShape(size = 16.dp))
//                    .padding(12.dp)
//            ) {
//                Column(
//                    verticalArrangement = Arrangement.SpaceBetween,
//                    horizontalAlignment = Alignment.Start,
//                ) {
////
//                    GoToMap(navController)
//                    KiteConditionInfoBox()
//                    Spacer(modifier = Modifier.height(12.dp))
//                    Box(modifier = Modifier.align(Alignment.BottomCenter)){
//                        NavBar(navController)
//                    }
//
//                }
//            }
//        }
//
//    }
//}

//@Composable
//fun InfoScreen(
//    navController: NavController
//) {
//
//            Box(
//                Modifier
//                    //.weight(1f)
//                    .fillMaxWidth()
//                    .background(color = DefaultBlue)
//                    .padding(16.dp)
//            ) {
//                Column(
//                    verticalArrangement = Arrangement.SpaceBetween,
//                    horizontalAlignment = Alignment.Start,
//                    modifier = Modifier.fillMaxSize()
//                ) {
//
//                GoToMap(navController)
//                Surface(
//                    Modifier
//                        //.fillMaxSize()
//                        .background(color = White, shape = RoundedCornerShape(size = 16.dp))
//                        .padding(16.dp)
//                ) {
//                    Column(
//                        verticalArrangement = Arrangement . SpaceBetween,
//                        horizontalAlignment = Alignment.Start,
//                    ) {
//                        // Content of your screen
//                        KiteConditionInfoBox()
//                        // NavBar fixed at the bottom
//                        Box(modifier = Modifier
//                            .align(Alignment.BottomCenter)) {
//                            NavBar(navController)
//                        }
//
//                    }
//
//
//                }
//
//            }
//
//
//
//        }
//     }







//@Composable
//fun InfoScreen(
//    navController: NavController
//) {
//    Column(
//        verticalArrangement = Arrangement.SpaceBetween,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Box(
//            Modifier
//                .weight(1f)
//                .fillMaxWidth()
//                .background(color = DefaultBlue)
//                .padding(16.dp)
//        ) {
//            Box(
//                Modifier
//                    .fillMaxSize()
//                    .background(color = White, shape = RoundedCornerShape(size = 16.dp))
//                    .padding(12.dp)
//            ) {
//                Column(
//                    verticalArrangement = Arrangement.SpaceBetween,
//                    horizontalAlignment = Alignment.Start,
//                ) {
//                    // Content of your screen
//                    GoToMap(navController)
//                    KiteConditionInfoBox()
//                }
//                Box(modifier = Modifier.align(
//                    Alignment.CenterEnd
//                )){
//                    NavBar(navController)
//                }
//            }
//
//        }
//
//    }
//}

//@Composable
//fun InfoScreen(navController: NavController) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = DefaultBlue)
//            .padding(16.dp)
//    ) {
//
//        Box(
//            modifier = Modifier
//                //.padding(16.dp)
//                .align(Alignment.TopStart)
//        ) {
//            GoToMap(navController)
//        }
//
//        // NavBar
//        Box(
//            modifier = Modifier
//               // .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
//                .align(Alignment.CenterStart)
//        ) {
//            KiteConditionInfoBox()
//        }
//
//
//        Box(
//            modifier = Modifier
//                //.padding(16.dp)
//                .align(Alignment.BottomCenter)
//        ) {
//            NavBar(navController)
//        }
//
//    }
//}

@Composable
fun InfoScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DefaultBlue)
            .padding(16.dp)
    ) {

        // Plasser GoToMap øverst
        GoToMap(navController)

        // Legg til et tomt rom som fyller tilgjengelig plass
        Spacer(modifier = Modifier.weight(1f))

        // Plasser KiteConditionInfoBox i midten
        KiteConditionInfoBox()

        // Legg til et tomt rom som fyller tilgjengelig plass
        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavBar(navController)
        }

    }
}
