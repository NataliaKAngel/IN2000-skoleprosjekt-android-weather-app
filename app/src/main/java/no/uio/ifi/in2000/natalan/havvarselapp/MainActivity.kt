
package no.uio.ifi.in2000.natalan.havvarselapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import no.uio.ifi.in2000.natalan.havvarselapp.ui.theme.HavvarselAppTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import no.uio.ifi.in2000.natalan.havvarselapp.model.predefinedSpots.PredefinedSpots
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

    private lateinit var connectivityObserver: ConnectivityObserver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Creates instance of NetworkConnectivityObserver
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        connectivityObserver.observe().onEach {
            println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMStatus is: $it")
        }.launchIn(lifecycleScope)
        setContent {

            HavvarselAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Collect the staus of the internet in a val
                    val staus by connectivityObserver.observe().collectAsState(
                        initial = ConnectivityObserver.Status.Unavailable
                    )

                    //Creates instances of datasources and repositories
                    val predefinedSpotsDataSource = PredefinedSpotsDataSource()
                    val locationForecastDataSource = LocationForecastDataSource()
                    val metAlertsDataSource = MetAlertsDataSource()
                    val weatherAPIRepository = WeatherAPIRepository(predefinedSpotsDataSource, locationForecastDataSource, metAlertsDataSource)

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
                    // Box that shows network status
                    // TODO need to make snackbar instead
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(text = "Network status: $staus")
                    }
                }
            }
        }
    }
}

