package no.uio.ifi.in2000.natalan.havvarselapp.ui.home

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.*

@Composable
fun HomeScreen(
    navController: NavController,
    homeScreenViewModel: HomeScreenViewModel
) {
    val context = LocalContext.current.applicationContext
    val mapView = createMapScreen(context)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        // Map
        AndroidView(
            factory = { mapView },
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.TopCenter)
        ) {
            TopBar(infoButtonClick = { navController.navigate("InfoScreen") })
        }

        // NavBar
        Box(
            modifier = Modifier
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.BottomCenter)
        ) {
            NavBarKart { selectedComponent ->
                // Her kan du utføre handlinger basert på den valgte komponenten
                when (selectedComponent) {
                    "Kart" -> {
                        // Gjør noe når "Kart" er valgt
                    }
                    "Favoritter" -> {
                        // Gjør noe når "Favoritter" er valgt
                    }
                    "Instillinger" -> {
                        // Gjør noe når "Instillinger" er valgt
                    }
                    // Legg til flere tilfeller etter behov for andre komponenter
                }
            }
        }
    }
}

@Composable
fun createMapScreen(context: Context): MapView {
    val mapView = remember { MapView(context) }

    mapView.mapboxMap.setCamera(
        CameraOptions.Builder()
            .center(Point.fromLngLat(7.99, 58.146))
            .pitch(0.0)
            .zoom(6.0)
            .bearing(0.0)
            .build()
    )
    return mapView
}