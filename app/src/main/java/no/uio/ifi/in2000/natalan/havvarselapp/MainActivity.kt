
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
import no.uio.ifi.in2000.natalan.havvarselapp.ui.home.HomeScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.spot.SpotScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            HavvarselAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Creates navController and NavHost
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "HomeScreen") {
                        // Navigere til de ulike
                        composable("HomeScreen") {HomeScreen(navController = navController)}
                        composable("InfoKiteForholdScreen") { SpotScreen(navController = navController) }
                    }
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



                    // Creates navController and NavHost
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "WarningScreen") {
                        // Navigere til de ulike
                        composable("HomeScreen") {LocationForecastScreen(navController = navController, locationForecastViewModel = locationForestViewModel)}
                        composable("WarningScreen") { MetAlertScreen(navController = navController, metAlertViewModel = metAlertViewModel) }
                    }*/