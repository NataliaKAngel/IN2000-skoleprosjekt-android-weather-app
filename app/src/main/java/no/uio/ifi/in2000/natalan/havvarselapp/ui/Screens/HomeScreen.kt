package no.uio.ifi.in2000.natalan.havvarselapp.ui.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.Components
import no.uio.ifi.in2000.natalan.havvarselapp.ui.map.MapScreen

class HomeScreen {
    @SuppressLint("NotConstructor")
    @Composable
    fun homeScreen() {
        val context = LocalContext.current.applicationContext
        val mapScreen = MapScreen()
        val mapView = mapScreen.createMapScreen(context)

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            // Map
            AndroidView(
                factory = { mapView },
                modifier = Modifier.fillMaxSize()
            )

            // Components
            Box(
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.TopCenter)
            ) {
                Components().TopBar()
            }

            // NavBar
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Components().navBar { selectedComponent ->
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
}