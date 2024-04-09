/*package no.uio.ifi.in2000.natalan.havvarselapp.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.Components
import no.uio.ifi.in2000.natalan.havvarselapp.ui.map.MapScreen

class HomeScreen {
    @Composable
    fun homeScreen(){
        val components = Components()
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(), // Fyller hele tilgjengelige plassen
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // TopBar
            components.TopBar()

            // Spacer for å skille TopBar og resten av innholdet
            Spacer(modifier = Modifier.weight(1f))

            // NavBar
            components.navBar()

            // Creates a map
            val mapScreen = MapScreen()
            val mapView = mapScreen.createMapScreen(this)

            // Set ContentView
            setContentView(mapView)
        }
    }
}*/

package no.uio.ifi.in2000.natalan.havvarselapp.ui.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.Components
import no.uio.ifi.in2000.natalan.havvarselapp.ui.map.MapScreen

class HomeScreen {
    @SuppressLint("NotConstructor")
    @Composable
    fun homeScreen() {
        val mapScreen = MapScreen()

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                // Components
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Components().TopBar()
                }

                // Map

                /*AndroidView(
                    factory = { context ->
                        mapScreen.createMapScreen(context)
                    },
                    modifier = Modifier.weight(1f)
                )*/

                // Spacer
                Spacer(modifier = Modifier.height(16.dp))

                // NavBar
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Components().navBar()
                }
            }
        }
    }
}