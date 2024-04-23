
package no.uio.ifi.in2000.natalan.havvarselapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.HavvarselAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import no.uio.ifi.in2000.natalan.havvarselapp.model.predefinedSpots.PredefinedSpots
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.locationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.metAlerts.MetAlertsDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.ui.favourite.FavouriteScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.favourite.FavouriteScreenViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.home.HomeScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.home.HomeScreenViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.info.InfoScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.settings.SettingsScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.spot.SpotScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.spot.SpotScreenViewModel
import no.uio.ifi.in2000.natalan.havvarselapp.ui.test.TestScreen
import no.uio.ifi.in2000.natalan.havvarselapp.ui.test.TestScreenViewModel

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
                    val predefinedSpots: List<PredefinedSpots> = listOf(
                        PredefinedSpots(
                            coordinates = "58.07037852078236, 6.778011069088529",
                            spotName = "Husebysanden",
                            cityName = "Lista",
                            optimalWindConditions = mapOf(
                                "min" to 180.0,
                                "max" to 270.0
                            )
                        ),
                        PredefinedSpots(
                            coordinates = "58.18857641754766, 8.086584207780076",
                            spotName = "Hamresanden",
                            cityName = "Kristiansand",
                            optimalWindConditions = mapOf(
                                "min" to 157.5,
                                "max" to 247.5
                            )
                        ),
                        PredefinedSpots(
                            coordinates = "58.07625203318467, 7.811114127684619",
                            spotName = "Høllesanden",
                            cityName = "Søgne",
                            optimalWindConditions = mapOf(
                                "min" to 157.5,
                                "max" to 247.5
                            )
                        ),
                        PredefinedSpots(
                            coordinates = "58.135371306063874, 7.034859484463499",
                            spotName = "Kvaviksanden",
                            cityName = "Lyngdal",
                            optimalWindConditions = mapOf(
                                "min" to 180.0,
                                "max" to 247.5
                            )
                        ),
                        PredefinedSpots(
                            coordinates = "58.0697096704821, 6.685477207426811",
                            spotName = "Kviljosanden",
                            cityName = "Lista",
                            optimalWindConditions = mapOf(
                                "min" to 112.5,
                                "max" to 292.5
                            )
                        ),
                        PredefinedSpots(
                            coordinates = "58.06814063685252, 6.731489560823652",
                            spotName = "Haviksanden",
                            cityName = "Lista",
                            optimalWindConditions = mapOf(
                                "min" to 112.5,
                                "max" to 247.5
                            )
                        )
                    )

                    //Creates instances of datasources and repositories
                    val locationForecastDataSource = LocationForecastDataSource()
                    val metAlertsDataSource = MetAlertsDataSource()
                    val weatherAPIRepository = WeatherAPIRepository(predefinedSpots, locationForecastDataSource, metAlertsDataSource)

                    // Creates instances of viewModels and Screens
                    val homeScreenViewModel = HomeScreenViewModel(weatherAPIRepository)
                    val favouriteScreenViewModel = FavouriteScreenViewModel(weatherAPIRepository)
                    val testScreenViewModel = TestScreenViewModel(weatherAPIRepository)

                    // Creates navController and NavHost
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "HomeScreen") {
                        // Navigating routes
                        composable("TestScreen") { TestScreen(testScreenViewModel = testScreenViewModel)}
                        composable("HomeScreen") { HomeScreen(navController = navController, homeScreenViewModel = homeScreenViewModel)}
                        composable("InfoScreen") { InfoScreen(navController = navController)}
                        composable("SpotScreen/{coordinates}",
                            arguments = listOf(navArgument("coordinates") { type = NavType.StringType })
                        ) { navBackStackEntry ->
                            val coordinates = navBackStackEntry.arguments?.getString("coordinates") ?: ""
                            val viewModel: SpotScreenViewModel = viewModel {SpotScreenViewModel(weatherAPIRepository, coordinates)}
                            SpotScreen(navController = navController, spotScreenViewModel = viewModel)}
                        composable("FavouriteScreen") { FavouriteScreen(navController = navController, favouriteScreenViewModel = favouriteScreenViewModel)}
                        composable("SettingsScreen") { SettingsScreen(navController = navController)}
                    }
                }
            }
        }
    }
}

