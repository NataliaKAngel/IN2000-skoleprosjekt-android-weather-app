package no.uio.ifi.in2000.natalan.havvarselapp.model.metAlerts;

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
public class MetAlertsViewModel : ViewModel() {
    @Composable
    fun HomeScreen(){
        Column {
            Text("Her kommer API informasjon")
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
    fun PreviewHomeScreen(){
        HomeScreen()
    }
}
