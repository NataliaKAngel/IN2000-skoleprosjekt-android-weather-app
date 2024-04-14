
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
import no.uio.ifi.in2000.natalan.havvarselapp.model.predefinedSpots.PredefinedSpots
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.locationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.metAlerts.MetAlertDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.ui.favourite.FavouriteScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.favourite.FavouriteScreenViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.home.HomeScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.home.HomeScreenViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.info.InfoScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.info.InfoScreenViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.settings.SettingsScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.settings.SettingsScreenViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.spot.SpotScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.spot.SpotScreenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            HavvarselAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Predefined spots
                    val predefinedSpots : List<PredefinedSpots> = listOf(
                        PredefinedSpots(coordinate = "lat=58&lon=8.1", spotName = "Hamresanden", cityName = "Kristiansand"),
                        PredefinedSpots(coordinate = "lat=60&lon=10.7", spotName = "Aker Brygge", cityName = "Oslo"),
                        //PredefinedSpots(coordinate = "", spotName = "", cityName = "")
                    )

                    //Creates instances of datasources and repositories
                    val locationForecastDataSource = LocationForecastDataSource()
                    val metAlertDataSource = MetAlertDataSource()
                    val weatherAPIRepository = WeatherAPIRepository(predefinedSpots, locationForecastDataSource, metAlertDataSource)

                    // Creates instances of viewModels and Screens
                    val homeScreenViewModel = HomeScreenViewModel(weatherAPIRepository)
                    val infoScreenViewModel = InfoScreenViewModel(weatherAPIRepository)
                    val favouriteScreenViewModel = FavouriteScreenViewModel(weatherAPIRepository)
                    val settingsScreenViewModel = SettingsScreenViewModel(weatherAPIRepository)
                    val spotScreenViewModel = SpotScreenViewModel(weatherAPIRepository)

                    // Creates navController and NavHost
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "HomeScreen") {
                     // Navigating routes
                        composable("HomeScreen") { HomeScreen(navController = navController, homeScreenViewModel = homeScreenViewModel) }
                        composable("InfoScreen") { InfoScreen(navController = navController, infoScreenViewModel = infoScreenViewModel) }
                        composable("SpotScreen") { SpotScreen(navController = navController, spotScreenViewModel = spotScreenViewModel)}
                        composable("FavouriteScreen") { FavouriteScreen(navController = navController, favouriteScreenViewModel = favouriteScreenViewModel)}
                        composable("SettingsScreen") { SettingsScreen(navController = navController, settingsScreenViewModel = settingsScreenViewModel)}
                    }
                }
            }
        }
    }
}