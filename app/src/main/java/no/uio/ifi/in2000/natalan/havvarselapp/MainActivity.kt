
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
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.locationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.metAlerts.MetAlertDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.ui.home.HomeScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.home.HomeScreenViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.info.InfoScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.info.InfoScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            HavvarselAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Creates instances of datasources and repositories
                    val locationForecastDataSource = LocationForecastDataSource()
                    val metAlertDataSource = MetAlertDataSource()
                    val weatherAPIRepository = WeatherAPIRepository(locationForecastDataSource, metAlertDataSource)

                    // Creates instances of viewModels and Screens
                    val homeScreenViewModel = HomeScreenViewModel(weatherAPIRepository)
                    val spotScreenViewModel = InfoScreenViewModel(weatherAPIRepository)

                    // Creates navController and NavHost
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "HomeScreen") {
                     // Navigating routes
                     composable("HomeScreen") { HomeScreen(navController = navController, homeScreenViewModel = homeScreenViewModel) }
                     composable("InfoScreen") { InfoScreen(navController = navController, infoScreenViewModel = spotScreenViewModel) }
                    }
                }
            }
        }
    }
}