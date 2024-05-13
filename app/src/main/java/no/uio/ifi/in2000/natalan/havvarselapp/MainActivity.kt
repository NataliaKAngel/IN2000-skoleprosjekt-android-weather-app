
package no.uio.ifi.in2000.natalan.havvarselapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.HavvarselAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
//import no.uio.ifi.in2000.natalan.havvarselapp.model.predefinedSpots.PredefinedSpots
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.WeatherAPIRepository
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.locationForecast.LocationForecastDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.metAlerts.MetAlertsDataSource
import no.uio.ifi.in2000.natalan.havvarselapp.data.weatherAPI.predefinedSpots.PredefinedSpotsDataSource
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

    //Creates instance of ConnectivityObserver
    private lateinit var connectivityObserver: ConnectivityObserver
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Creates instance of NetworkConnectivityObserver
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        setContent {

            HavvarselAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Monitors the internet connection
                    val status by connectivityObserver.observe().collectAsState(
                        initial = ConnectivityObserver.Status.Unavailable
                    )

                    // Creates instance of snackbar
                    val snackbarHostState = remember { SnackbarHostState() }
                    val scope = rememberCoroutineScope()

                    // Show snackbar when connection is lost or unavailable
                    LaunchedEffect(status) {
                        delay(3000) // Delay for 3 seconds

                        if (status == ConnectivityObserver.Status.Lost) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Network connection: $status"
                                )
                            }
                        }
                        if (status == ConnectivityObserver.Status.Unavailable) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Network connection: $status"
                                )
                            }
                        }
                    }

                    //Creates instances of datasources and repositories
                    val predefinedSpotsDataSource = PredefinedSpotsDataSource()
                    val locationForecastDataSource = LocationForecastDataSource()
                    val metAlertsDataSource = MetAlertsDataSource()
                    val weatherAPIRepository = WeatherAPIRepository(predefinedSpotsDataSource, locationForecastDataSource, metAlertsDataSource)

                    // Creates instances of viewModels and Screens
                    val homeScreenViewModel = HomeScreenViewModel(weatherAPIRepository)
                    val favouriteScreenViewModel = FavouriteScreenViewModel(weatherAPIRepository)
                    val testScreenViewModel = TestScreenViewModel(weatherAPIRepository)

                    Scaffold (
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                    ) {
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
}

