
package no.uio.ifi.in2000.natalan.havvarselapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.HavvarselAppTheme
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForecast.LocationForecastRepository
import no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts.MetAlertDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts.MetAlertRepository
import no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast.LocationForecastViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast.LocationForecastScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.metAlerts.MetAlertScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.metAlerts.MetAlertViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HavvarselAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Creates instances of datasources and repositories
                    val locationForecastDataSource = LocationForecastDataSource()
                    val locationForecastRepository = LocationForecastRepository(locationForecastDataSource)
                    val metAlertDataSource = MetAlertDataSource()
                    val metAlertRepository = MetAlertRepository(metAlertDataSource)

                    // Creates instances of viewModels and Screens
                    val locationForestViewModel = LocationForecastViewModel(locationForecastRepository)
                    val metAlertViewModel = MetAlertViewModel(metAlertRepository)

                    // Creates navController and NavHost
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "WarningScreen") {
                        // Navigere til de ulike
                        composable("HomeScreen") {LocationForecastScreen(navController = navController, locationForecastViewModel = locationForestViewModel)}
                        composable("WarningScreen") { MetAlertScreen(navController = navController, metAlertViewModel = metAlertViewModel) }
                    }
                }
            }
        }
    }
}
