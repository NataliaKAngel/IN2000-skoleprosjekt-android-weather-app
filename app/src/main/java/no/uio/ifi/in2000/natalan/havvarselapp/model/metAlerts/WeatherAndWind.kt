package no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class WeatherAndWind {
    @Composable
    fun HomeScreen(){
        Column {
            Text("Her kommer API informasjon")

        }
    }

    @Preview
    @Composable
    fun PreviewHomeScreen(){
       HomeScreen()
    }
}

