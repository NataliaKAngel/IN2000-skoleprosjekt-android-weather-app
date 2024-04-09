
package no.uio.ifi.in2000.natalan.havvarselapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.HavvarselAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.locationForecast.LocationForecastRepository
import no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts.MetAlertDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.metAlerts.MetAlertRepository
import no.uio.ifi.in2000.natalan.havvarselapp.ui.Screens.HomeScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.components.Components
import no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast.LocationForecastViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.locationForecast.LocationForecastScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.map.MapScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.metAlerts.MetAlertScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.metAlerts.MetAlertViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            HavvarselAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val homeScreen = HomeScreen()
                    homeScreen.homeScreen()
                }
            }
        }
    }
}

                    /*// Creates instances of datasources and repositories
                    val locationForecastDataSource = LocationForecastDataSource()
                    val locationForecastRepository = LocationForecastRepository(locationForecastDataSource)
                    val metAlertDataSource = MetAlertDataSource()
                    val metAlertRepository = MetAlertRepository(metAlertDataSource)

                    // Creates instances of viewModels and Screens
                    val locationForestViewModel = LocationForecastViewModel(locationForecastRepository)
                    val metAlertViewModel = MetAlertViewModel(metAlertRepository)



                    /*// Creates navController and NavHost
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "WarningScreen") {
                        // Navigere til de ulike
                        composable("HomeScreen") {LocationForecastScreen(navController = navController, locationForecastViewModel = locationForestViewModel)}
                        composable("WarningScreen") { MetAlertScreen(navController = navController, metAlertViewModel = metAlertViewModel) }
                    }*/