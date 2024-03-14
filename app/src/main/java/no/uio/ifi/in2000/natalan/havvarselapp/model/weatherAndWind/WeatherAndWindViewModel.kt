package no.uio.ifi.in2000.natalan.havvarselapp.model.weatherAndWind

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

class WeatherAndWindViewModel : ViewModel() {
    @Composable
    fun WeatherAndWindScreen(){
        Column {
            Text("Her kommer  WeatherAndWind API informasjon")
            Button(
                onClick = {  },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Knapp til neste skjerm")
            }
        }
    }

    @Preview
    @Composable
    fun PreviewWeatherAndWindScreen(){
        WeatherAndWindScreen()
    }
}

