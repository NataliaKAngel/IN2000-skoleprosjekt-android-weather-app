package no.uio.ifi.in2000.natalan.havvarselapp.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.Components

class InfoKiteForholdScreen() {
    @Composable
    fun infoScreen(navController: NavController){
        Box(
            Modifier
                .width(360.dp)
                .height(640.dp)
                .background(color = Color(0xFF96CFF5))
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Components
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Components().goToMap { navController.navigate("HomeScreen") }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Box{
                    Components().kiteForholdInfoBox()
                }

                Spacer(modifier = Modifier.height(12.dp))

                // NavBar
                Box(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                ) {
                    Components().navBarKart { selectedComponent ->
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

}
